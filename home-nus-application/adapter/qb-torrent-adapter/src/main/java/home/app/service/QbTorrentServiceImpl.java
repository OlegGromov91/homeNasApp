package home.app.service;

import home.app.model.qbTorrent.Torrent;
import home.app.repository.TorrentRepository;
import home.app.repository.UserRepository;
import home.app.service.rest.RestQbTorrentService;
import home.app.utils.converters.ApplicationTypeConverter;
import home.app.view.qbTorrent.TorrentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Primary
public class QbTorrentServiceImpl implements QbTorrentService {

    @Autowired
    private RestQbTorrentService restQbTorrentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TorrentRepository torrentRepository;
    @Autowired
    private ApplicationTypeConverter converter;

    /**
     * TODO Джоба, которая чистит загруженные торренты, будет кидать в телеграм сообщение о том, что файл загружен
     */
    @Transactional
    public List<TorrentView> getInfoAboutAllDownloadingTorrents() {
        restQbTorrentService.loginBefore();
        List<TorrentView> torrentViews = restQbTorrentService.getAllDownloadingTorrents();
        torrentViews.forEach(this::updateTorrent);
        return torrentViews;
    }


    @Override
    public void pauseTorrent(String torrentHashName) {
        restQbTorrentService.loginBefore();
        restQbTorrentService.pauseTorrent(torrentHashName);
    }

    @Override
    public void resumeTorrent(String torrentHashName) {
        restQbTorrentService.loginBefore();
        restQbTorrentService.resumeTorrent(torrentHashName);
    }

    @Override
    public void downloadTorrent(@NotNull byte[] file,
                                @NotNull String fileName,
                                @NotNull String savePath,
                                String category) {
        restQbTorrentService.loginBefore();
        if (Objects.nonNull(category)) {
            restQbTorrentService.downloadTorrent(file, fileName, category, savePath);
        } else {
            restQbTorrentService.downloadTorrent(file, fileName, null, savePath);
        }
    }

    @Override
    public void deleteTorrent(String torrentHashName) {
        restQbTorrentService.loginBefore();
        restQbTorrentService.deleteTorrent(torrentHashName, Boolean.FALSE);
    }

    @Override
    public void deleteTorrentAndData(String torrentHashName) {
        restQbTorrentService.loginBefore();
        restQbTorrentService.deleteTorrent(torrentHashName, Boolean.TRUE);
    }

    private void updateTorrent(TorrentView torrentView) {
        Optional<Torrent> torrent = torrentRepository.findTorrentByHashAndName(torrentView.getHash(), torrentView.getName());
        Torrent saveCandidate = converter.convert(torrentView, Torrent.class);
        torrent.ifPresent(value -> saveCandidate.setId(value.getId()));
        torrentRepository.save(saveCandidate);
    }
}
