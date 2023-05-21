package home.app;

import home.app.model.qbTorrent.Torrent;
import home.app.model.user.ApplicationUser;
import home.app.model.user.TelegramUser;
import home.app.repository.TorrentRepository;
import home.app.repository.UserRepository;
import home.app.utils.converters.ApplicationTypeConverter;
import home.app.view.qbTorrent.SizeView;
import home.app.view.qbTorrent.TorrentView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TestData {

    @Autowired
    private ApplicationTypeConverter converter;
    @Autowired
    private TorrentRepository torrentRepository;
    @Autowired
    UserRepository userRepository;


    public void preFilledAllData(){
        preFilledTestUsers();
        preFilledTestTorrents();
    }

    public void preFilledTestUsers() {


        List<ApplicationUser> users = List.of(
                ApplicationUser.builder()
                        .firstName("TestUser")
                        .telegramUser(buildTgUser(544908050L, "Oleg"))
                        .build()
        );

        userRepository.saveAll(users);

        Optional<ApplicationUser> user = userRepository.findByTelegramUserId(544908050L);
        log.debug("user with id 544908050L isPresent? = " + user.isPresent());
    }

    public void preFilledTestTorrents() {

        List<TorrentView> torrentViews = List.of(TorrentView.builder()
                        .hash("3cd13f28cdc138b871658b4bcb2d4ab0ee7956e3")
                        .name("Mock")
                        .rootPath("MockRootPath")
                        .rootPath("MockContentPath")
                        .rawTotalSize(444L)
                        .convertedTotalSize(SizeView.builder().size(3.4).unit("Mb").build())
                        .downloadedPercent(50.)
                        .convertedDownloadedSize(SizeView.builder().size(5.5).unit("Mb").build())
                        .downloadedPercent(3.3)
                        .state("Приостановлен")
                        .build()
                ,
                TorrentView.builder()
                        .hash("3cd13f28cdc138b87165gggbcb2d4ab0ee7956e3")
                        .name("ASdkjdfhbl-kjm3k,fkkkkkkkkkkkkkkkkkkkkkk")
                        .rootPath("MockRootPath")
                        .rootPath("MockContentPath")
                        .rawTotalSize(444L)
                        .convertedTotalSize(SizeView.builder().size(3.4).unit("Mb").build())
                        .downloadedPercent(50.)
                        .convertedDownloadedSize(SizeView.builder().size(5.5).unit("Mb").build())
                        .downloadedPercent(3.3)
                        .state("pausedDL")
                        .build()
                ,
                TorrentView.builder()
                        .hash("3cd13f28cdc138b33365gggbcb2d4ab0ee7956e3")
                        .name("SSSSSSfgklji4ds-sdakfl.eefd")
                        .rootPath("MockRootPath")
                        .rootPath("MockContentPath")
                        .rawTotalSize(444L)
                        .convertedTotalSize(SizeView.builder().size(3.4).unit("Mb").build())
                        .downloadedPercent(50.)
                        .convertedDownloadedSize(SizeView.builder().size(5.5).unit("Mb").build())
                        .downloadedPercent(3.3)
                        .state("downloading")
                        .build()
        );
        List<Torrent> torrents = torrentViews.parallelStream()
                .map(torrent -> converter.convert(torrent, Torrent.class))
                .collect(Collectors.toList());

        torrentRepository.saveAll(torrents);
    }


    private TelegramUser buildTgUser(Long id, String name) {
        return TelegramUser.builder()
                .id(id)
                .firstName(name)
                .isBot(false)
                .build();
    }
}
