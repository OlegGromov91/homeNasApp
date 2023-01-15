package home.app.utils.downloader;

public interface FileDownloader {

    byte[] downloadFile(String filePath);
    byte[] downloadFile(String filePath, String url);

}
