package home.app.model.files;

import home.app.model.files.types.ApplicationFileTypes;
import home.app.model.user.ApplicationUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "APPLICATION_FILES")
@DiscriminatorValue(value = "TelegramFile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramFiles extends ApplicationFile {

    @Column(name = "TELEGRAM_FILE_TYPE")
    @Enumerated(value = EnumType.STRING)
    private ApplicationFileTypes telegramFileType;

    @ManyToOne
    private ApplicationUser user;

    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @Column(name = "CREATING_DATE", nullable = false)
    private LocalDateTime creatingDate;
}
