package home.app.model.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "APP_USERS")
@DiscriminatorValue(value = "TelegramUser")
@Data
public class TelegramUser extends ApplicationUser {

    @Column(name = "USER_HASH", unique = true, nullable = false, updatable = false)
    private String hash;
}
