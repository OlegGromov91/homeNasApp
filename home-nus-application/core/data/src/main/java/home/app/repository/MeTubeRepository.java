package home.app.repository;

import home.app.model.meTube.MeTubeVideo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeTubeRepository extends CrudRepository<MeTubeVideo, Long> {

    Optional<MeTubeVideo> findByUrl(String url);
}
