package home.app.model.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "APP_USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "SECOND_NAME")
    private String secondName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "TG_ID")),
            @AttributeOverride(name = "firstName", column = @Column(name = "TG_FIRST_NAME")),
            @AttributeOverride(name = "secondName", column = @Column(name = "TG_SECOND_NAME")),
            @AttributeOverride(name = "userName", column = @Column(name = "TG_USER_NAME")),
            @AttributeOverride(name = "isBot", column = @Column(name = "TG_IS_BOT")),
    })
    private TelegramUser telegramUser;

    @Email
    @Column(name = "EMAIL")
    private String email;


}
