package home.app.model.user;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TelegramUser {

    private Long id;

    private String firstName;

    private String secondName;

    private String userName;

    private Boolean isBot;
}
