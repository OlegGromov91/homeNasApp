package home.app.model.qbTorrent.enums;

public enum SystemTorrentType {

    VIDEO("Видео"),
    FILM("Фильм"),
    GAME("Игра"),
    PROGRAM("Программа"),
    OTHER("Другое"),
    ;

    SystemTorrentType(String description) {
        this.description = description;
    }

    private final String description;
}
