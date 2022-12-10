package home.app.model.qbTorrent;

import java.util.Arrays;
import java.util.Objects;

public enum Status {

    PAUSED("pausedDL", "Приостановлен"),
    SEED_FINDING("stalledDL", "Поиск сидов"),
    DISTRIBUTED("stalledUP", "Раздается"),
    DOWNLOADING("downloading", "Загружается"),
    DOWNLOADED("pausedUP", "Загружаен"),
    UNKNOWN(null, "Неизвестно");

    private final String qbTorrentName;
    private final String description;

    Status(String qbTorrentName, String description) {
        this.qbTorrentName = qbTorrentName;
        this.description = description;
    }

    public Status getStatusByQBName(String qbTorrentName) {
        return Arrays.stream(Status.values())
                .filter(status -> Objects.equals(status.qbTorrentName, qbTorrentName))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
