package home.app.model.disk;

import home.app.model.disk.types.NasDiskType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NAS_DISK")
@DiscriminatorValue(value = "TorrentDisk")
@Data
public class TorrentDisk extends NasDisk {

    @Column(name = "FREE_SPACE")
    private Double freeSpace;
    @Column(name = "UNIT")
    private String unit;

    @Override
    public NasDiskType initialDiskType() {
        return NasDiskType.TORRENT_DISK;
    }
}
