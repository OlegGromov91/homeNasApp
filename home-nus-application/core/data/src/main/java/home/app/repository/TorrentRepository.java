package home.app.repository;

import home.app.model.qbTorrent.Torrent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TorrentRepository extends CrudRepository<Torrent, Long> {

    Optional<Torrent> findTorrentByHash(String hash);

    Optional<Torrent> findTorrentByHashAndName(String hash, String name);

}
