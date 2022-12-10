package home.app.model.nas.disk;

import home.app.model.nas.disk.types.NasDiskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "NAS_DISK")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "NAS_DISK_TYPE")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class NasDisk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DISK_ID")
    private Long id;

    private final NasDiskType diskType = initialDiskType();

    public abstract NasDiskType initialDiskType();

}
