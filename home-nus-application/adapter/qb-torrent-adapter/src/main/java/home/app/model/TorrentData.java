package home.app.model;

import home.app.model.qbTorrent.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class TorrentData {
    // TODO: парсинг инфо о диске
    private String hash;
    private String name;
    private String rootPath;
    private String contentPath;
    private Long rawTotalSize;
    private Size convertedTotalSize;
    private Integer rawDownloadedSize;
    private Size convertedDownloadedSize;
    private Double downloadedPercent;
    private String state;


}
