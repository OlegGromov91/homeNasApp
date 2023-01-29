package home.app.repository;

import home.app.model.user.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByTelegramUserId(Long id);
}
