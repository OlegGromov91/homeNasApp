package home.app.view.meTube;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MeTubeVideoView {

    private Long id;
    private String name;
    private LocalDateTime creatingDate;
    private String url;
    private String status;
    private String videoFormat;
}
