package home.app.service.resolvers;

import home.app.exception.RestQbTorrentException;
import home.app.service.QbTorrentService;
import home.app.service.rest.RestTelegramBotService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

import static home.app.service.enums.FileSuffix.TORRENT;

@Component
public class TorrentFileBotResolver extends FileBotResolver {

    @Autowired
    private RestTelegramBotService restTelegramBotService;
    @Autowired
    private QbTorrentService qbTorrentService;

    @Override
    public void resolve(Update update) {
        processDoc(update.getMessage());
    }

    @Override
    public Class<? extends BotResolver> type() {
        return this.getClass();
    }

    @Override
    public boolean identifyResolver(Message tgMessage) {
        Document document = tgMessage.getDocument();
        if (Objects.nonNull(document)) {
            return Objects.equals(document.getMimeType(), TORRENT.getMimeType()) ||
                    document.getFileName().endsWith(TORRENT.getSuffix());
        }
        return false;
    }

    private void processDoc(Message telegramMessage) {
        Document telegramDoc = telegramMessage.getDocument();
        String fileId = telegramDoc.getFileId();
        JSONObject fileInfo = restTelegramBotService.getFileInfo(fileId);

        if (fileInfo.keySet().contains("ok")) {
            String filePath = String.valueOf(fileInfo
                    .getJSONObject("result")
                    .getString("file_path"));

            byte[] file = restTelegramBotService.downloadFileFromTelegram(filePath);

            qbTorrentService.downloadTorrent(file, telegramDoc.getFileName(), null);
        } else {
            throw new RestQbTorrentException("when try get fileInfo response get wrong code");
        }
    }
}
