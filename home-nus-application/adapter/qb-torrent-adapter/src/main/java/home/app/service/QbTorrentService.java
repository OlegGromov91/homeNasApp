package home.app.service;

import home.app.model.TorrentCategory;
import home.app.model.TorrentData;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public interface QbTorrentService {


    List<TorrentData> getInfoAboutAllDownloadingTorrents();

    void pauseTorrent(String torrentHashName);

    void resumeTorrent(String torrentHashName);

    void downloadTorrent(@NotNull byte[] file, @NotNull String fileName, TorrentCategory torrentCategory);

    void deleteTorrent(String torrentHashName);

    void deleteTorrentAndData(String torrentHashName);


}
