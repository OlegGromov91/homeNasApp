package home.app;

import home.app.exception.RestQbTorrentException;
import home.app.service.rest.RestQbTorrentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BotResolveService {

    @Autowired
    private RestQbTorrentService restQbTorrentService;


    public void resolve(Update update) {
        processDoc(update.getMessage());
    }

    private void processDoc(Message telegramMessage) {
        Document telegramDoc = telegramMessage.getDocument();
        String fileId = telegramDoc.getFileId();
        JSONObject fileInfo = restQbTorrentService.getFileInfo(fileId);

        if (fileInfo.keySet().contains("ok")) {
            String filePath = String.valueOf(fileInfo
                    .getJSONObject("result")
                    .getString("file_path"));

            byte[] file = restQbTorrentService.downloadFileFromTelegram(filePath);

            restQbTorrentService.downloadTorrent(file, telegramDoc);
        } else {
            throw new RestQbTorrentException("when try get fileInfo response get wrong code");
        }
    }
}
