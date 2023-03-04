package home.app.model.meTube.enums;

import lombok.Getter;

@Getter
public enum MeTubeVideoStatus {
    IN_QUEUE("В очереди"),
    DOWNLOADING("Загружается"),
    DONE("Загружен"),
    ERROR("Ошибка"),
    ;

    private final String format;

    MeTubeVideoStatus(String format) {
        this.format = format;
    }

}
