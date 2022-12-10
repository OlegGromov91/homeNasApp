package home.app.model.qbTorrent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TORRENT")
@Data
@SuperBuilder
public class Torrent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TORRENT_ID")
    private Long id;

    @Column(name = "HASH_NAME")
    private String hash;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private TorrentDisk torrentDisk;

    @Column(name = "TORRENT_NAME")
    private String name;

    @Column(name = "ROOT_PATH")
    private String rootPath;  //save_path

    @Column(name = "CONTENT_PATH")
    private String contentPath;

    @Column(name = "TOTAL_SIZE")
    private BigDecimal totalSize;

    @Column(name = "DOWNLOADED_SIZE")
    private BigDecimal downloaded;

    @Column(name = "DOWNLOADED_PROCENT", columnDefinition = "FLOAT(0,0)")
    private Float downloadedPercent;  //считается динамически

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private Status state;

    @Column(name = "SYSTEM_TORRENT_TYPE")
    @Enumerated(value = EnumType.STRING)
    private SystemTorrentType systemTorrentType;


}
