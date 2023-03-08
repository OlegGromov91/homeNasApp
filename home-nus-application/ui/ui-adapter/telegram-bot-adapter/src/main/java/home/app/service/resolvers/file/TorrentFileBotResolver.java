package home.app.service.resolvers.file;

import com.google.common.base.Strings;
import home.app.exception.RestQbTorrentException;
import home.app.model.files.TelegramFiles;
import home.app.model.files.types.ApplicationFileTypes;
import home.app.model.user.ApplicationUser;
import home.app.repository.TelegramFilesRepository;
import home.app.repository.UserRepository;
import home.app.service.resolvers.BotResolver;
import home.app.service.rest.RestTelegramBotService;
import org.json.JSONObject;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static home.app.service.enums.FileSuffix.TORRENT;

@Component
public class TorrentFileBotResolver extends FileBotResolver {

    @Autowired
    private RestTelegramBotService restTelegramBotService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TelegramFilesRepository telegramFilesRepository;

    private InlineKeyboardMarkup inlineKeyboardMarkup;


    @Value("#{${home-application.q-bit-torrent.torrent-category.mappings}}")
    private Map<String, String> torrentCategories;
    @Value("${home-application.q-bit-torrent.torrent-category.is-need-download-selectivity-by-user}")
    private Boolean isNeedDownloadSelectivityByUser;
    @Value("${home-application.q-bit-torrent.torrent-category.root-dataset-media-path}")
    private String rootDatasetPath;

    private final String FILE_MESSAGE = "Выберите тип загружаемого файла";
    private final String CALL_BACK_MESSAGE = "Файл %s добавлен в загрузки";
    private static final String SUFFIX_BUTTON_NAME = "TG_";
    private static final String WITHOUT_CATEGORIES = "Загрузить";


    @PostConstruct
    public void init() {
        if (Strings.isNullOrEmpty(rootDatasetPath)) {
            throw new BeanCreationException("Не заполнен параметр \"root-dataset-media-path\" в файле \"application.yml\"!!!!");
        }
        this.inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons;

        if (Objects.isNull(torrentCategories) || torrentCategories.isEmpty()) {
            torrentCategories = new HashMap<>();
            torrentCategories.put(WITHOUT_CATEGORIES, rootDatasetPath);
            buttons = torrentCategories.keySet().stream().map(this::buildButton).collect(Collectors.toList());
        } else {
            buttons = torrentCategories.keySet().stream().map(this::buildButton).collect(Collectors.toList());

            String userTemplate = (isNeedDownloadSelectivityByUser) ? "%s/" : "";
            Map<String, String> preParedTorrentCategory = new HashMap<>();
            for (String key : torrentCategories.keySet()) {
                String path = rootDatasetPath + userTemplate + torrentCategories.get(key);
                preParedTorrentCategory.putIfAbsent(key, path);
            }
            torrentCategories = preParedTorrentCategory;
        }
        inlineKeyboardMarkup.setKeyboard(List.of(buttons));
    }

    @Override
    public Class<? extends BotResolver> type() {
        return this.getClass();
    }

    @Override
    public boolean identifyResolver(Update update) {
        return super.identifyResolver(update) && identifyFileType(update);
    }

    @Override
    public boolean identifyCallBackResolver(Update update) {
        if (update.hasCallbackQuery() && !Strings.isNullOrEmpty(update.getCallbackQuery().getData())) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String buttonData = callbackQuery.getData().replace(SUFFIX_BUTTON_NAME, "");
            return !torrentCategories.isEmpty() && torrentCategories.containsKey(buttonData);
        }
        return false;
    }

    @Override
    protected boolean identifyFileType(Update update) {
        if (update.getMessage().hasDocument()) {
            Document document = update.getMessage().getDocument();
            return Objects.equals(document.getMimeType(), TORRENT.getMimeType()) || document.getFileName().endsWith(TORRENT.getSuffix());
        }
        return false;
    }

    @Override
    protected SendMessage processMessage(Message telegramMessage) {
        Document document = telegramMessage.getDocument();
        String fileId = document.getFileId();
        JSONObject fileInfo = restTelegramBotService.getFileInfo(fileId);
        Long userId = telegramMessage.getFrom().getId();
        Long chatId = telegramMessage.getChatId();

        ApplicationUser user = userRepository.findByTelegramUserId(userId).orElseThrow(() -> new RuntimeException("can not find user with id " + userId));

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(FILE_MESSAGE);
        message.setReplyMarkup(inlineKeyboardMarkup);

        if (fileInfo.keySet().contains("ok")) {
            String filePath = String.valueOf(fileInfo
                    .getJSONObject("result")
                    .getString("file_path"));

            TelegramFiles torrentFile = TelegramFiles.builder()
                    .telegramFileType(ApplicationFileTypes.TORRENT)
                    .user(user)
                    .filePath(filePath)
                    .fileName(document.getFileName())
                    .creatingDate(LocalDateTime.now())
                    .build();

            telegramFilesRepository.save(torrentFile);

        } else {
            throw new RestQbTorrentException("when try get fileInfo response get wrong code");
        }
        return message;
    }

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {


        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String data = callbackQuery.getData().replace(SUFFIX_BUTTON_NAME, "");
        String fileName = uploadFileToTorrentApplication(
                userId,
                torrentCategories.keySet().stream().filter(key -> key.equals(data)).findFirst().orElse(null),
                torrentCategories.get(data));

        EditMessageText message = new EditMessageText();

        message.setText(String.format(CALL_BACK_MESSAGE, fileName));
        message.setChatId(chatId);
        message.setMessageId(messageId);

        return message;
    }

    private String uploadFileToTorrentApplication(Long userId, String path, String category) {
        ApplicationUser user = userRepository.findByTelegramUserId(userId).orElseThrow(RuntimeException::new);
        List<TelegramFiles> tgFiles = telegramFilesRepository.findByUser(user);
        TelegramFiles tgFile = tgFiles.stream().max(Comparator.comparing(TelegramFiles::getCreatingDate)).orElseThrow(RuntimeException::new);

        byte[] file = restTelegramBotService.downloadFileFromTelegram(tgFile.getFilePath());

        qbTorrentService.downloadTorrent(file, tgFile.getFileName(), path, category);
        return tgFile.getFileName();
    }

    private InlineKeyboardButton buildButton(String text) {
        return InlineKeyboardButton.builder().text(text).callbackData(SUFFIX_BUTTON_NAME + text).build();
    }


}
