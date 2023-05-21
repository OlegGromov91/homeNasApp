package home.app.service.rest;

import home.app.exception.RestQbTorrentException;
import home.app.utils.downloader.DefaultFileDownloader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class RestTelegramBotService {

    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.file-info}")
    private String fileInfoUri;
    @Value("${bot.file-storage}")
    private String fileStorageUri;
    @Value("${home-application.q-bit-torrent.request-timeout}")
    private Long requestTimeout;

    @Autowired
    private WebClient webClient;
    @Autowired
    private DefaultFileDownloader defaultFileDownloader;

    public JSONObject getFileInfo(String fileId) {
        String fileInfoUri = this.fileInfoUri.replace("{token}", botToken)
                .replace("{fileId}", fileId);
        try {
            String response = webClient.get()
                    .uri(fileInfoUri)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .block(Duration.of(requestTimeout, ChronoUnit.MINUTES));
            return new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestQbTorrentException("get error when try request to telegram with url " + fileInfoUri + " " + e);
        }

    }

    public byte[] downloadFileFromTelegram(String filePath) {
        String storageUri = fileStorageUri.replace("{token}", botToken)
                .replace("{filePath}", filePath);
        return defaultFileDownloader.downloadFile(storageUri);
    }


}
