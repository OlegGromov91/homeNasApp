package home.app.model.qbTorrent;

import home.app.model.qbTorrent.enums.Status;
import home.app.model.qbTorrent.enums.TorrentCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TORRENT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Torrent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "HASH_NAME")
    private String hash;

    @Column(name = "TORRENT_NAME")
    private String name;

    @Column(name = "ROOT_PATH")
    private String rootPath;

    @Column(name = "CONTENT_PATH")
    private String contentPath;

    @Column(name = "RAW_TOTAL_SIZE")
    private Long rawTotalSize;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "size", column = @Column(name = "CONVERTED_TOTAL_SIZE")),
            @AttributeOverride(name = "unit", column = @Column(name = "CONVERTED_TOTAL_UNIT")),
    })
    private Size convertedTotalSize;

    @Column(name = "RAW_DOWNLOADED_SIZE")
    private Integer rawDownloadedSize;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "size", column = @Column(name = "CONVERTED_DOWNLOADED_SIZE")),
            @AttributeOverride(name = "unit", column = @Column(name = "CONVERTED_DOWNLOADED_UNIT")),
    })
    private Size convertedDownloadedSize;


    @Column(name = "DOWNLOADED_PROCENT")
    private Double downloadedPercent;

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private Status state;

    @Column(name = "TORRENT_CATEGORY")
    @Enumerated(value = EnumType.STRING)
    private TorrentCategory torrentCategory;

}
