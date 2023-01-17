package home.app.model.qbTorrent;

import com.fasterxml.jackson.annotation.JsonBackReference;

import home.app.model.disk.TorrentDisk;
import home.app.model.qbTorrent.enums.Status;
import home.app.model.qbTorrent.enums.SystemTorrentType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TORRENT")
@Data
@Builder
public class Torrent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TORRENT_ID")
    private Long id;

    @Column(name = "HASH_NAME")
    private String hash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISK_ID", nullable = false)
    @JsonBackReference
    private TorrentDisk torrentDisk;

    @Column(name = "TORRENT_NAME")
    private String name;

    @Column(name = "ROOT_PATH")
    private String rootPath;  //save_path

    @Column(name = "CONTENT_PATH")
    private String contentPath;

    @Column(name = "TOTAL_SIZE")
    private Long totalSize;

    @Column(name = "DOWNLOADED_SIZE")
    private Long downloaded;

    @Column(name = "DOWNLOADED_PROCENT")
    private Float downloadedPercent;  //считается динамически

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private Status state;

    @Column(name = "SYSTEM_TORRENT_TYPE")
    @Enumerated(value = EnumType.STRING)
    private SystemTorrentType systemTorrentType;

}
