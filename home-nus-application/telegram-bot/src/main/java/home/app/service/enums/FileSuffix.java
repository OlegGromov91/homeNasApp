package home.app.service.enums;

import lombok.Getter;

@Getter
public enum FileSuffix {

    TORRENT(".torrent", "application/x-bittorrent"),
    TXT(".txt", "text/plain"),
    ;

    private final String suffix;
    private final String mimeType;

    FileSuffix(String suffix, String mimeType) {
        this.suffix = suffix;
        this.mimeType = mimeType;
    }
}
