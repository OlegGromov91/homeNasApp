package home.app.view.qbTorrent;

import home.app.model.qbTorrent.Size;
import home.app.model.qbTorrent.enums.Status;
import home.app.model.qbTorrent.enums.TorrentCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;


@Getter
@Setter
@Builder
public class TorrentDataView {
    // TODO: парсинг инфо о диске
    private Long id;
    private String hash;
    private String name;
    private String rootPath;
    private String contentPath;
    private Long rawTotalSize;
    private Size convertedTotalSize;
    private Integer rawDownloadedSize;
    private Size convertedDownloadedSize;
    private Double downloadedPercent;
    private String state;
    private String torrentCategory;

    @Override
    public String toString() {
        return "\tИмя='" + name + '\'' +
                ",\n Размер файла =" + convertedTotalSize.getSize() + " " + convertedTotalSize.getUnit() +
                ", Скаченно=" + convertedDownloadedSize.getSize() + " " + convertedDownloadedSize.getUnit() +
                " | " + downloadedPercent + "% " +
                ", Статус='" + extractStatus() +
                ", Категория='" + extractCategory() +
                '\''
                + "\n_________________\n"
                ;

    }

    private String extractStatus() {
        return Arrays.stream(Status.values()).filter(status -> status.getQbTorrentName().equals(state)).findFirst().orElse(Status.UNKNOWN).getDescription();
    }

    private String extractCategory() {
        return Arrays.stream(TorrentCategory.values()).filter(category -> category.getDescription().equals(this.torrentCategory)).findFirst().orElse(TorrentCategory.UNKNOWN).getDescription();
    }
}
