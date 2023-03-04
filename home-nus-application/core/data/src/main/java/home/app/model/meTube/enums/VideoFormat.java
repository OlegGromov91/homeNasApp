package home.app.model.meTube.enums;

import lombok.Getter;

@Getter
public enum VideoFormat {

    MP_4("mp4"),
    MP_3("mp3"),
    ;

    private final String format;

    VideoFormat(String format) {
        this.format = format;
    }

}
