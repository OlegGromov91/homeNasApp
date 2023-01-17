package home.app.service;

import home.app.exception.TorrentException;
import home.app.model.TorrentCategory;
import home.app.model.TorrentData;
import home.app.service.rest.RestQbTorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class QbTorrentService {

    @Autowired
    private RestQbTorrentService restQbTorrentService;

    public List<TorrentData> getInfoAboutAllDownloadingTorrents() {
        return restQbTorrentService.getAllDownloadingTorrents();
    }

    public void pauseTorrent(String torrentHashName) {
        restQbTorrentService.pauseTorrent(torrentHashName);
    }

    public void resumeTorrent(String torrentHashName) {
        restQbTorrentService.resumeTorrent(torrentHashName);
    }

    public void downloadTorrent(@NotNull byte[] file,
                                @NotNull String fileName,
                                String category) {
        if (Objects.nonNull(category)) {
            TorrentCategory torrentCategory = extractTorrentCategory(category);
            restQbTorrentService.downloadTorrent(file, fileName, torrentCategory.getCategoryDescription(), torrentCategory.getSavePath());
        } else {
            restQbTorrentService.downloadTorrent(file, fileName, null, null);
        }
    }

    private TorrentCategory extractTorrentCategory(String category) {
        return Arrays.stream(TorrentCategory.values())
                .filter(torrentCategory -> torrentCategory.getCategoryDescription().equals(category))
                .findFirst().orElseThrow(() -> new TorrentException("Can not find category " + category));
    }


}
