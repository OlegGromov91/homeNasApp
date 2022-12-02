package home.app.model.qbTorrent;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "TORRENT")
@Data
@SuperBuilder
public class Torrent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "torrent_id")
    private Long id;

    @Column(name = "hash_name")
    private String hash;

}
