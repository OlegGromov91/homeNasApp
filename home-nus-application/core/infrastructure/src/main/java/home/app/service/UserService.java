package home.app.service;

import home.app.model.user.ApplicationUser;
import home.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ApplicationUser getUserByTelegramId(Long telegramId) {
        return userRepository.findByTelegramUserId(telegramId).orElseThrow(() -> new RuntimeException("can not find user with id " + telegramId));
    }
}
