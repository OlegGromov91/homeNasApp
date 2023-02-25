package home.app.utils.converters;

import home.app.model.qbTorrent.Size;
import home.app.model.qbTorrent.Torrent;
import home.app.model.qbTorrent.enums.Status;
import home.app.model.qbTorrent.enums.TorrentCategory;
import home.app.view.qbTorrent.TorrentDataView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TorrentDataViewToTorrentConverter implements Converter<TorrentDataView, Torrent> {

    @Override
    public Torrent convert(TorrentDataView source) {
        return Torrent.builder()
                .id(source.getId())
                .hash(source.getHash())
                .name(source.getName())
                .rootPath(source.getRootPath())
                .contentPath(source.getContentPath())
                .rawTotalSize(source.getRawTotalSize())
                .convertedTotalSize(Size.builder()
                        .size(source.getConvertedTotalSize().getSize())
                        .unit(source.getConvertedTotalSize().getUnit())
                        .build())
                .rawDownloadedSize(source.getRawDownloadedSize())
                .convertedDownloadedSize(Size.builder()
                        .size(source.getConvertedDownloadedSize().getSize())
                        .unit(source.getConvertedDownloadedSize().getUnit())
                        .build())
                .downloadedPercent(source.getDownloadedPercent())
                .state(extractStatus(source.getState()))
                .torrentCategory(extractCategory(source.getTorrentCategory()))
                .build();
    }

    private Status extractStatus(String state) {
        return Arrays.stream(Status.values()).filter(status -> status.getQbTorrentName().equals(state)).findFirst().orElse(Status.UNKNOWN);
    }

    private TorrentCategory extractCategory(String torrentCategory) {
        return Arrays.stream(TorrentCategory.values()).filter(category -> category.getDescription().equals(torrentCategory)).findFirst().orElse(TorrentCategory.UNKNOWN);
    }
}
