package home.app.model.nas.disk.types;

public enum NasDiskType implements DiskType {

    TORRENT_DISK("Диск для торрента"),
    DATA_DISK("Диск для данных");

    private final String name;

    NasDiskType(String name) {
        this.name = name;
    }
}
