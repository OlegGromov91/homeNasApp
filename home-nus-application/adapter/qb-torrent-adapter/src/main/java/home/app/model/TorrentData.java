package home.app.model;

import home.app.utils.calculator.information.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TorrentData {
// TODO: парсинг инфо о диске
    private String hash;
    private String name;
    private String rootPath;  //save_path
    private String contentPath;
    private Long totalSize;
    private Size sizeTotal;
    private Integer downloaded;
    private Size sizeDownloaded;
    private Double downloadedPercent;  //считается динамически
    private String state;
    private String telegramUserHash;


}
