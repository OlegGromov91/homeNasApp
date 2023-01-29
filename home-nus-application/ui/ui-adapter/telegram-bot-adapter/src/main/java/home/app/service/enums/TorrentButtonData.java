package home.app.service.enums;

import home.app.model.qbTorrent.enums.SystemTorrentType;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Getter
public enum TorrentButtonData {
    VIDEO(InlineKeyboardButton.builder().text(SystemTorrentType.VIDEO.getDescription()).callbackData("TG_" + SystemTorrentType.VIDEO.name()).build()),
    FILM(InlineKeyboardButton.builder().text(SystemTorrentType.FILM.getDescription()).callbackData("TG_" + SystemTorrentType.FILM.name()).build()),
    GAME(InlineKeyboardButton.builder().text(SystemTorrentType.GAME.getDescription()).callbackData("TG_" + SystemTorrentType.GAME.name()).build()),
    PROGRAM(InlineKeyboardButton.builder().text(SystemTorrentType.PROGRAM.getDescription()).callbackData("TG_" + SystemTorrentType.PROGRAM.name()).build()),
    OTHER(InlineKeyboardButton.builder().text(SystemTorrentType.OTHER.getDescription()).callbackData("TG_" + SystemTorrentType.OTHER.name()).build()),
    ;

    TorrentButtonData(InlineKeyboardButton button) {
        this.button = button;

    }

    private final InlineKeyboardButton button;
    public static final String SUFFIX_BUTTON_NAME = "TG_";

}
