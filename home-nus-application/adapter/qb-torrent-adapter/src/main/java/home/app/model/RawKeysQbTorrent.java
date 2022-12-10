package home.app.model;

import lombok.Getter;

@Getter
public enum RawKeysQbTorrent {
    NAME("name", String.class),
    ROOT_PATH("save_path", String.class),
    CONTENT_PATH("content_path", String.class),
    TOTAL_SIZE("total_size", Long.class),
    DOWNLOADED("downloaded", Integer.class),
    STATE("state", String.class);

    RawKeysQbTorrent(String key, Class<?> value) {
        this.key = key;
        this.value = value;
    }

    private final String key;
    private final Class value;

}
