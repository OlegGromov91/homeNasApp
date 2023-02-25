package home.app.utils.converters.torrent;

import home.app.model.qbTorrent.Torrent;
import home.app.view.qbTorrent.SizeView;
import home.app.view.qbTorrent.TorrentView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TorrentToTorrentViewConverter implements Converter<Torrent, TorrentView> {

    @Override
    public TorrentView convert(Torrent source) {
        return TorrentView.builder()
                .id(source.getId())
                .hash(source.getHash())
                .name(source.getName())
                .rootPath(source.getRootPath())
                .contentPath(source.getContentPath())
                .rawTotalSize(source.getRawTotalSize())
                .convertedTotalSize(SizeView.builder()
                        .size(source.getConvertedTotalSize().getSize())
                        .unit(source.getConvertedTotalSize().getUnit())
                        .build())
                .rawDownloadedSize(source.getRawDownloadedSize())
                .convertedDownloadedSize(SizeView.builder()
                        .size(source.getConvertedDownloadedSize().getSize())
                        .unit(source.getConvertedDownloadedSize().getUnit())
                        .build())
                .downloadedPercent(source.getDownloadedPercent())
                .state(source.getState().getDescription())
                .torrentCategory(source.getTorrentCategory().getDescription())
                .build();
    }


}
