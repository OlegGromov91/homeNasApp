package home.app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TorrentData {

    private String hash;
    private String name;
    private String rootPath;  //save_path
    private String contentPath;
    private Long totalSize;
    private Integer downloaded;
    private Float downloadedPercent;  //считается динамически
    private String state;
    private String systemTorrentType;

}
