package home.app.service;

import home.app.view.qbTorrent.TorrentView;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public interface QbTorrentService {


    List<TorrentView> getInfoAboutAllDownloadingTorrents();

    void pauseTorrent(String torrentHashName);

    void resumeTorrent(String torrentHashName);

    void downloadTorrent(@NotNull byte[] file, @NotNull String fileName, String category, String savePath);

    void deleteTorrent(String torrentHashName);

    void deleteTorrentAndData(String torrentHashName);


}
