package home.app.model.meTube;

import home.app.model.meTube.enums.MeTubeVideoStatus;
import home.app.model.meTube.enums.VideoFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ME_TUBE_VIDEO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeTubeVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "VIDEO_NAME")
    private String name;

    @Column(name = "CREATING_DATE")
    private LocalDateTime creatingDate;

    @Column(name = "URL", nullable = false, unique = true)
    private String url;

    @Column(name = "VIDEO_STATUS")
    @Enumerated(value = EnumType.STRING)
    private MeTubeVideoStatus status;

    @Column(name = "VIDEO_FORMAT")
    @Enumerated(value = EnumType.STRING)
    private VideoFormat videoFormat;

}
