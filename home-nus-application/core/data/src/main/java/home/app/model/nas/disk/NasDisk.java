package home.app.model.nas.disk;

import home.app.model.nas.disk.types.DiskType;
import home.app.model.nas.disk.types.NasDiskType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "NAS_DISK")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "nas_disk_type")
@Data
@SuperBuilder
public abstract class NasDisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nas_disk_id")
    private Long id;

    @Column(name = "disk_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private final NasDiskType diskType = initialDiskType();

    public abstract NasDiskType initialDiskType();

}
