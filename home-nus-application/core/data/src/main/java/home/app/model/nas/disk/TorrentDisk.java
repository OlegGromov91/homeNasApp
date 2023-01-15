package home.app.model.nas.disk;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import home.app.model.nas.disk.types.NasDiskType;
import home.app.model.qbTorrent.Torrent;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "NAS_DISK")
@DiscriminatorValue(value = "TorrentDisk")
@Data
public class TorrentDisk extends NasDisk {

    @Column(name = "FREE_SPACE")
    private Double freeSpace;
    @Column(name = "UNIT")
    private String unit;
    @OneToMany(mappedBy = "torrentDisk", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Torrent> torrents = new HashSet<>();

    @Override
    public NasDiskType initialDiskType() {
        return NasDiskType.TORRENT_DISK;
    }
}
