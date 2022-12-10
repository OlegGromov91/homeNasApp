package home.app.model.qbTorrent;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import home.app.model.nas.disk.NasDisk;
import home.app.model.nas.disk.types.NasDiskType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "NAS_DISK")
@DiscriminatorValue(value = "TorrentDisk")
@Data
public class TorrentDisk extends NasDisk {

    @Column(name = "FREE_SPACE")
    private String freeSpace;

    @OneToMany(mappedBy = "torrentDisk", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Torrent> torrents = new HashSet<>();

    @Override
    public NasDiskType initialDiskType() {
        return NasDiskType.TORRENT_DISK;
    }
}
