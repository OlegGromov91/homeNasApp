package home.app.service;

import home.app.exception.TorrentException;
import home.app.model.TorrentCategory;
import home.app.model.TorrentData;
import home.app.service.rest.RestQbTorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Service
@Primary
public class QbTorrentServiceImpl implements QbTorrentService {

    @Autowired
    private RestQbTorrentService restQbTorrentService;

    public List<TorrentData> getInfoAboutAllDownloadingTorrents() {
        return restQbTorrentService.getAllDownloadingTorrents();
    }

    @Override
    public void pauseTorrent(String torrentHashName) {
        restQbTorrentService.pauseTorrent(torrentHashName);
    }

    @Override
    public void resumeTorrent(String torrentHashName) {
        restQbTorrentService.resumeTorrent(torrentHashName);
    }

    @Override
    public void downloadTorrent(@NotNull byte[] file,
                                @NotNull String fileName,
                                TorrentCategory torrentCategory) {
        if (Objects.nonNull(torrentCategory)) {
            restQbTorrentService.downloadTorrent(file, fileName, torrentCategory.getDescription(), torrentCategory.getSavePath());
        } else {
            restQbTorrentService.downloadTorrent(file, fileName, null, null);
        }
    }

    @Override
    public void deleteTorrent(String torrentHashName) {
        restQbTorrentService.deleteTorrent(torrentHashName, Boolean.FALSE);
    }

    @Override
    public void deleteTorrentAndData(String torrentHashName) {
        restQbTorrentService.deleteTorrent(torrentHashName, Boolean.TRUE);
    }

    private TorrentCategory extractTorrentCategory(String category) {
        try {
            return TorrentCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new TorrentException("Can not find category " + category);
        }
    }
}
