//package home.app.service.rest;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.telegram.telegrambots.meta.api.objects.Document;
//import org.telegram.telegrambots.meta.api.objects.Message;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//@Service
//public class FileStorage {
//
//    @Value("${bot.token}")
//    private String token;
//    @Value("${bot.file-info}")
//    private String fileInfoUri;
//    @Value("${bot.file-storage}")
//    private String fileStorageUri;
//
//    @Autowired
//    private RestQbTorrentService restQbTorrentService;
//
//    public byte[] processDoc(Message telegramMessage) {
//        Document telegramDoc = telegramMessage.getDocument();
//        String fileId = telegramDoc.getFileId();
//        ResponseEntity<String> response = getFilePath(fileId);
//        if (response.getStatusCode() == HttpStatus.OK) {
//            JSONObject jsonObject = new JSONObject(response.getBody());
//            String filePath = String.valueOf(jsonObject
//                    .getJSONObject("result")
//                    .getString("file_path"));
//            byte[] file = downloadFile(filePath);
//            System.out.println(file.length);
//            restQbTorrentService.downloadTorrent(file, telegramDoc);
//            return file;
//
//        } else {
//            throw new RuntimeException("Bad response from telegram service: " + response);
//        }
//    }
//
//    private byte[] downloadFile(String filePath) {
//        String fullUri = fileStorageUri.replace("{token}", token)
//                .replace("{filePath}", filePath);
//        URL urlObj = null;
//        try {
//            urlObj = new URL(fullUri);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//
//        //TODO подумать над оптимизацией
//        try (InputStream is = urlObj.openStream()) {
//            return is.readAllBytes();
//        } catch (IOException e) {
//            throw new RuntimeException(urlObj.toExternalForm(), e);
//        }
//    }
//
//    private ResponseEntity<String> getFilePath(String fileId) {
//
//        restQbTorrentService.getFileInfo(fileId);
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<String> request = new HttpEntity<>(headers);
//
//        return restTemplate.exchange(
//                fileInfoUri,
//                HttpMethod.GET,
//                request,
//                String.class,
//                token, fileId
//        );
//    }
//}
