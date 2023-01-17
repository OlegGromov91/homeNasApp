package home.app.utils.downloader;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class DefaultFileDownloader implements FileDownloader {

    @Override
    public byte[] downloadFile(String filePath) {
        URL urlObj;
        try {
            urlObj = new URL(filePath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try (InputStream stream = urlObj.openStream()) {
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(urlObj.toExternalForm(), e);
        }
    }

}
