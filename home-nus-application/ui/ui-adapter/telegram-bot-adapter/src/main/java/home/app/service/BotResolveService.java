package home.app.service;

import home.app.exception.RestQbTorrentException;
import home.app.service.rest.RestTelegramBotService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BotResolveService {

    @Autowired
    private RestTelegramBotService restTelegramBotService;
    @Autowired
    private QbTorrentService qbTorrentService;

    public void resolve(Update update) {
        processDoc(update.getMessage());
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
