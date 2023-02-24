package home.app.service;

import home.app.model.TorrentCategory;
import home.app.model.TorrentData;
import home.app.model.qbTorrent.Size;
import home.app.service.rest.RestQbTorrentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
//@Primary
public class MockQbTorrentService implements QbTorrentService {

    @Autowired
    private RestQbTorrentService restQbTorrentService;

    //TODO у телеграмма могут быть проблеммы с длинной строки и в ActionsTorrentMenuBotResolver лучше использовать id из базы
    public List<TorrentData> getInfoAboutAllDownloadingTorrents() {
        return List.of(TorrentData.builder()
                .hash("3cd13f28cdc138b871658b4bcb2d4ab0ee7956e3")
                .name("Mock")
                .rootPath("MockRootPath")
                .rootPath("MockContentPath")
                .rawTotalSize(444L)
                .convertedTotalSize(Size.builder().size(3.4).unit("Mb").build())
                .downloadedPercent(50.)
                .convertedDownloadedSize(Size.builder().size(5.5).unit("Mb").build())
                .downloadedPercent(3.3)
                .state("Приостановлен")
                .build()
                ,
                TorrentData.builder()
                        .hash("3cd13f28cdc138b87165gggbcb2d4ab0ee7956e3")
                        .name("ASdkjdfhbl-kjm3k,fkkkkkkkkkkkkkkkkkkkkkk")
                        .rootPath("MockRootPath")
                        .rootPath("MockContentPath")
                        .rawTotalSize(444L)
                        .convertedTotalSize(Size.builder().size(3.4).unit("Mb").build())
                        .downloadedPercent(50.)
                        .convertedDownloadedSize(Size.builder().size(5.5).unit("Mb").build())
                        .downloadedPercent(3.3)
                        .state("pausedDL")
                        .build()
                ,
                TorrentData.builder()
                        .hash("3cd13f28cdc138b33365gggbcb2d4ab0ee7956e3")
                        .name("SSSSSSfgklji4ds-sdakfl.eefd")
                        .rootPath("MockRootPath")
                        .rootPath("MockContentPath")
                        .rawTotalSize(444L)
                        .convertedTotalSize(Size.builder().size(3.4).unit("Mb").build())
                        .downloadedPercent(50.)
                        .convertedDownloadedSize(Size.builder().size(5.5).unit("Mb").build())
                        .downloadedPercent(3.3)
                        .state("downloading")
                        .build()
        );
    }

    public void downloadTorrent(@NotNull byte[] file,
                                @NotNull String fileName,
                                TorrentCategory torrentCategory) {
        log.debug("Its OK");
    }

    public void pauseTorrent(String torrentHashName) {
        log.debug("Its OK");
    }

    public void resumeTorrent(String torrentHashName) {
        log.debug("Its OK");
    }

    public void deleteTorrent(String torrentHashName) {
        log.debug("Its OK");
    }

    public void deleteTorrentAndData(String torrentHashName) {
        log.debug("Its OK");
    }


}
