package home.app.service.resolvers;

import com.google.common.base.Strings;
import home.app.exception.RestQbTorrentException;
import home.app.model.files.TelegramFiles;
import home.app.model.files.types.ApplicationFileTypes;
import home.app.model.qbTorrent.enums.SystemTorrentType;
import home.app.model.user.ApplicationUser;
import home.app.repository.TelegramFilesRepository;
import home.app.repository.UserRepository;
import home.app.service.QbTorrentService;
import home.app.service.rest.RestTelegramBotService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Objects;

import static home.app.service.enums.FileSuffix.TORRENT;
import static home.app.service.enums.TorrentButtonData.SUFFIX_BUTTON_NAME;

@Component
public class TorrentFileBotResolver extends FileBotResolver {

    @Autowired
    private RestTelegramBotService restTelegramBotService;
    @Autowired
    private QbTorrentService qbTorrentService;
    @Autowired
    private InlineKeyboardMarkup torrentInlineKeyboardMarkup;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TelegramFilesRepository telegramFilesRepository;

    private final String FILE_MESSAGE = "Выберите тип загружаемого файла";
    private final String CALL_BACK_MESSAGE = "Файл добавлен в загрузки";

    @Override
    public SendMessage resolve(Update update) {
        Message message = update.getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (Objects.nonNull(message)) {
            return processDocument(update.getMessage(), update.getMessage().getFrom().getId(), update.getMessage().getChatId());
        } else if (Objects.nonNull(callbackQuery)) {
            return processCallbackQuery(callbackQuery, callbackQuery.getFrom().getId(), callbackQuery.getFrom().getId());
        }
        throw new UnsupportedOperationException("can not find data for resolver + " + type());
    }

    @Override
    public Class<? extends BotResolver> type() {
        return this.getClass();
    }

    @Override
    public boolean identifyResolver(Update update) {
        Message message = update.getMessage();
        if (Objects.nonNull(message)) {
            Document document = message.getDocument();
            return Objects.nonNull(document) &&
                    (Objects.equals(document.getMimeType(), TORRENT.getMimeType()) || document.getFileName().endsWith(TORRENT.getSuffix()));
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (Objects.nonNull(callbackQuery) && !Strings.isNullOrEmpty(callbackQuery.getData())) {
            String buttonData = callbackQuery.getData().replace(SUFFIX_BUTTON_NAME, "");
            try {
                SystemTorrentType.valueOf(buttonData);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }

    private SendMessage processDocument(Message telegramMessage, Long userId, Long chatId) {
        Document document = telegramMessage.getDocument();
        String fileId = document.getFileId();
        JSONObject fileInfo = restTelegramBotService.getFileInfo(fileId);

        ApplicationUser user = userRepository.findByTelegramUserId(userId).orElseThrow(RuntimeException::new);


        SendMessage message = new SendMessage();

        message.setChatId(chatId);

        message.setText(FILE_MESSAGE);
        message.setReplyMarkup(torrentInlineKeyboardMarkup);
////
////
////        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
////        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
////
////        List<InlineKeyboardButton> rowInline = new ArrayList<>();
////
////        InlineKeyboardButton button = new InlineKeyboardButton();
////        InlineKeyboardButton button2 = new InlineKeyboardButton();
////        button.setText("yes");
////        button.setCallbackData("TG_VIDEO");
////
////        button2.setText("no");
////        button2.setCallbackData("TG_BUTTON");
////        rowInline.add(button);
////        rowInline.add(button2);
////        rows.add(rowInline);
////        inlineKeyboardMarkup.setKeyboard(rows);
////

        if (fileInfo.keySet().contains("ok")) {
            String filePath = String.valueOf(fileInfo
                    .getJSONObject("result")
                    .getString("file_path"));

            TelegramFiles torrentFile = TelegramFiles.builder()
                    .telegramFileType(ApplicationFileTypes.TORRENT)
                    .user(user)
                    .filePath(filePath)
                    .fileName(document.getFileName())
                    .build();

            telegramFilesRepository.save(torrentFile);

        } else {
            throw new RestQbTorrentException("when try get fileInfo response get wrong code");
        }
        return message;
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery, Long userId, Long chatId) {
        ApplicationUser user = userRepository.findByTelegramUserId(userId).orElseThrow(RuntimeException::new);
        TelegramFiles tgFile = telegramFilesRepository.findByUser(user).orElseThrow(RuntimeException::new);

        byte[] file = restTelegramBotService.downloadFileFromTelegram(tgFile.getFilePath());

        qbTorrentService.downloadTorrent(file, tgFile.getFileName(), null);

        SendMessage message = new SendMessage();

        message.setChatId(chatId);

        message.setText(CALL_BACK_MESSAGE);
        return message;
    }

}
