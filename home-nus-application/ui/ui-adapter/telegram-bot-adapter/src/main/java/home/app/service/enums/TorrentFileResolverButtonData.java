package home.app.service.enums;

import home.app.model.qbTorrent.enums.TorrentCategory;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Getter
public enum TorrentFileResolverButtonData {
    TG_VIDEO(InlineKeyboardButton.builder().text(home.app.model.TorrentCategory.VIDEO.getDescription()).callbackData("TG_" + TorrentCategory.VIDEO.name()).build(), home.app.model.TorrentCategory.VIDEO),
    TG_FILM(InlineKeyboardButton.builder().text(TorrentCategory.FILM.getDescription()).callbackData("TG_" + TorrentCategory.FILM.name()).build(), home.app.model.TorrentCategory.FILM),
    TG_GAME(InlineKeyboardButton.builder().text(TorrentCategory.GAME.getDescription()).callbackData("TG_" + TorrentCategory.GAME.name()).build(), home.app.model.TorrentCategory.GAME),
    TG_PROGRAM(InlineKeyboardButton.builder().text(TorrentCategory.PROGRAM.getDescription()).callbackData("TG_" + TorrentCategory.PROGRAM.name()).build(), home.app.model.TorrentCategory.PROGRAM),
    TG_MUSIC(InlineKeyboardButton.builder().text(TorrentCategory.MUSIC.getDescription()).callbackData("TG_" + TorrentCategory.MUSIC.name()).build(), home.app.model.TorrentCategory.MUSIC),
    TG_SERIAL(InlineKeyboardButton.builder().text(TorrentCategory.SERIAL.getDescription()).callbackData("TG_" + TorrentCategory.SERIAL.name()).build(), home.app.model.TorrentCategory.SERIAL),
    TG_OPERATION_SYSTEM(InlineKeyboardButton.builder().text(TorrentCategory.OPERATION_SYSTEM.getDescription()).callbackData("TG_" + TorrentCategory.OPERATION_SYSTEM.name()).build(), home.app.model.TorrentCategory.OPERATION_SYSTEM),
    TG_OTHER(InlineKeyboardButton.builder().text(TorrentCategory.OTHER.getDescription()).callbackData("TG_" + TorrentCategory.OTHER.name()).build(), home.app.model.TorrentCategory.OTHER),
    ;

    TorrentFileResolverButtonData(InlineKeyboardButton button, home.app.model.TorrentCategory torrentCategory) {
        this.button = button;
        this.torrentCategory = torrentCategory;
    }

    private final InlineKeyboardButton button;
    private final home.app.model.TorrentCategory torrentCategory;
    public static final String SUFFIX_BUTTON_NAME = "TG_";

}
