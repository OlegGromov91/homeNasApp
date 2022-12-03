package home.app.model.qbTorrent;

import home.app.model.nas.disk.NasDisk;
import home.app.model.nas.disk.types.NasDiskType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NAS_DISK")
@DiscriminatorValue(value = "TorrentDisk")
@Data
@SuperBuilder
public class TorrentDisk extends NasDisk {

    @Column(name = "FREE_SPACE")
    private String freeSpace;

    @Override
    public NasDiskType initialDiskType() {
        return NasDiskType.TORRENT_DISK;
    }
}
