package home.app.model;

import home.app.model.qbTorrent.enums.Status;
import home.app.utils.calculator.information.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

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


    @Override
    public String toString() {
        return "\tИмя='" + name + '\'' +
                ", Размер файла =" + sizeTotal.getSize() + " " + sizeTotal.getUnit() +
                ", Скаченно=" + sizeDownloaded.getSize() + " " + sizeDownloaded.getUnit() +
                " | " + downloadedPercent + "% " +
                ", Статус='" + Arrays.stream(Status.values()).filter(status -> status.getQbTorrentName().equals(state)).findFirst().orElse(Status.UNKNOWN).getDescription() + '\''
                + "\n_________________\n"
                ;

    }
}
