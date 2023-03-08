package home.app.model;

import lombok.Getter;

@Getter
public enum TorrentCategory {

    //MUSIC("Музыка", "/downloads/media/music"),
    MUSIC("Музыка", "/media/music"),
    VIDEO("Видео", "/downloads/media/video"),
    FILM("Фильм", "/downloads/media/films"),
    SERIAL("Сериал", "/downloads/media/serials"),
    GAME("Игры", "/downloads/games"),
    PROGRAM("Программы", "/downloads/program"),
    OPERATION_SYSTEM("Операционные системы", "/downloads/systems"),
    OTHER("Другое", "/downloads/other"),
    ;

    TorrentCategory(String description, String savePath) {
        this.description = description;
        this.savePath = savePath;
    }

    private final String description;
    private final String savePath;
}
