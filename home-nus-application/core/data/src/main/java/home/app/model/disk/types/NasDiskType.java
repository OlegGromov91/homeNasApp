package home.app.model.disk.types;

import lombok.Getter;

@Getter
public enum NasDiskType implements DiskType {

    TORRENT_DISK("Диск для торрента"),
    DATA_DISK("Диск для данных");

    private final String name;

    NasDiskType(String name) {
        this.name = name;
    }
}
