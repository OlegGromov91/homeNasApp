package home.app.model;

import lombok.Getter;

@Getter
public enum TorrentCategory {

    MUSIC("Музыка", "/downloads/media/music"),
    FILM("Фильм", "/downloads/media/films"),
    SERIAL("Сериал", "/downloads/media/serials"),
    GAME("Игры", "/downloads/games"),
    PROGRAM("Программы", "/downloads/program"),
    OPERATION_SYSTEM("Операционные системы", "/downloads/systems"),
    OTHER("Другое", "/downloads/other"),
    ;

    TorrentCategory(String categoryDescription, String savePath) {
        this.categoryDescription = categoryDescription;
        this.savePath = savePath;
    }

    private final String categoryDescription;
    private final String savePath;
}
