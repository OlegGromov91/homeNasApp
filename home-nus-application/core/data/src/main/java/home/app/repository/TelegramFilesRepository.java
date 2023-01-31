package home.app.repository;

import home.app.model.files.TelegramFiles;
import home.app.model.user.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelegramFilesRepository extends CrudRepository<TelegramFiles, Long> {

    List<TelegramFiles> findByUser(ApplicationUser user);

    void deleteAllByUser(ApplicationUser user);
}
