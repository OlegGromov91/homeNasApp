package home.app.model.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "APPLICATION_FILES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "APPLICATION_FILES_TYPE")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApplicationFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;


}
