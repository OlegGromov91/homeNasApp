package home.app.repository;

import home.app.model.meTube.MeTubeVideo;
import home.app.model.meTube.enums.MeTubeVideoStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeTubeRepository extends CrudRepository<MeTubeVideo, Long> {

    Optional<MeTubeVideo> findByUrl(String url);

    List<MeTubeVideo> findByStatusIn(List<MeTubeVideoStatus> statuses);

}
