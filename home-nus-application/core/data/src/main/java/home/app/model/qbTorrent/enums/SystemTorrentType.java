package home.app.model.qbTorrent.enums;

import lombok.Getter;

@Getter
public enum SystemTorrentType {

    MUSIC("Музыка"),
    FILM("Фильм"),
    VIDEO("Видео"),
    SERIAL("Сериал"),
    GAME("Игры"),
    PROGRAM("Программы"),
    OPERATION_SYSTEM("Операционные системы"),
    OTHER("Другое"),
    ;

    SystemTorrentType(String description) {
        this.description = description;
    }

    private final String description;
}
