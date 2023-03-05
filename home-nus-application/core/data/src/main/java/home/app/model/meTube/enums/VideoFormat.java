package home.app.model.meTube.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VideoFormat {

    MP_4("mp4"),
    MP_3("mp3"),
    ;

    private final String format;

    VideoFormat(String format) {
        this.format = format;
    }

    public static VideoFormat extractVideoFormat(String format) {
        return Arrays.stream(VideoFormat.values()).filter(f -> f.getFormat().equals(format)).findFirst().orElse(VideoFormat.MP_4);
    }

}
