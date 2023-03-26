package home.app.service.resolvers.file;

import com.google.common.base.Strings;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static home.app.service.enums.FileSuffix.TORRENT;

@Component
public class TorrentFileBotResolver extends FileBotResolver {


    private InlineKeyboardMarkup inlineKeyboardMarkup;


    @Value("#{${home-application.q-bit-torrent.torrent-category.mappings}}")
    private Map<String, String> torrentCategories;
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
        createTorrentCategoriesIfNull();

        Map<String, String> buttonData = new HashMap<>();
        Map<String, String> preParedTorrentCategory = new HashMap<>();

        for (String key : this.torrentCategories.keySet()) {
            buttonData.put(key, SUFFIX_BUTTON_NAME + key);
            preParedTorrentCategory.putIfAbsent(key, rootDatasetPath + torrentCategories.get(key));
        }

        this.torrentCategories = preParedTorrentCategory;
        this.inlineKeyboardMarkup = buildKeyboardKeyboardMarkup(buttonData);

    }

    @Override
    public boolean identifyMessageResolver(Update update) {
        return super.identifyMessageResolver(update) && identifyFileType(update);
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
        Document document = extractDocumentFromMessage(telegramMessage);
        String fileId = document.getFileId();
        String fileName = document.getFileName();
        Long userId = extractUserIdFromMessage(telegramMessage);

        commonService.getFileInfo(fileId, fileName, userId);
        return getPreFilledMessage(telegramMessage)
                .text(FILE_MESSAGE)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {

        Long userId = callbackQuery.getFrom().getId();

        String data = callbackQuery.getData().replace(SUFFIX_BUTTON_NAME, "");
        String fileName = commonService.uploadFileToTorrentApplication(
                userId,
                torrentCategories.keySet().stream().filter(key -> key.equals(data)).findFirst().orElse(null),
                torrentCategories.get(data));

        return getPreFilledCallbackMessage(callbackQuery)
                .text(String.format(CALL_BACK_MESSAGE, fileName))
                .build();

    }


    private void createTorrentCategoriesIfNull() {
        if (Objects.isNull(torrentCategories) || torrentCategories.isEmpty()) {
            torrentCategories = new HashMap<>();
            torrentCategories.put(WITHOUT_CATEGORIES, rootDatasetPath);
        }
    }

}
