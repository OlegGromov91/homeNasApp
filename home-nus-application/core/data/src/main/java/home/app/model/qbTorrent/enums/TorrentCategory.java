package home.app.model.qbTorrent.enums;

import lombok.Getter;

@Getter
public enum TorrentCategory {

    MUSIC("Музыка"),
    FILM("Фильм"),
    VIDEO("Видео"),
    SERIAL("Сериал"),
    GAME("Игры"),
    PROGRAM("Программы"),
    OPERATION_SYSTEM("Операционные системы"),
    OTHER("Другое"),
    UNKNOWN("Неизвестно");

    TorrentCategory(String description) {
        this.description = description;
    }

    private final String description;
}
