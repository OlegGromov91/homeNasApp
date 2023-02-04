package home.app.service.enums;

import home.app.model.TorrentCategory;
import home.app.model.qbTorrent.enums.SystemTorrentType;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Getter
public enum TorrentFileResolverButtonData {
    TG_VIDEO(InlineKeyboardButton.builder().text(TorrentCategory.VIDEO.getDescription()).callbackData("TG_" + SystemTorrentType.VIDEO.name()).build(), TorrentCategory.VIDEO),
    TG_FILM(InlineKeyboardButton.builder().text(SystemTorrentType.FILM.getDescription()).callbackData("TG_" + SystemTorrentType.FILM.name()).build(), TorrentCategory.FILM),
    TG_GAME(InlineKeyboardButton.builder().text(SystemTorrentType.GAME.getDescription()).callbackData("TG_" + SystemTorrentType.GAME.name()).build(), TorrentCategory.GAME),
    TG_PROGRAM(InlineKeyboardButton.builder().text(SystemTorrentType.PROGRAM.getDescription()).callbackData("TG_" + SystemTorrentType.PROGRAM.name()).build(), TorrentCategory.PROGRAM),
    TG_MUSIC(InlineKeyboardButton.builder().text(SystemTorrentType.MUSIC.getDescription()).callbackData("TG_" + SystemTorrentType.MUSIC.name()).build(), TorrentCategory.MUSIC),
    TG_SERIAL(InlineKeyboardButton.builder().text(SystemTorrentType.SERIAL.getDescription()).callbackData("TG_" + SystemTorrentType.SERIAL.name()).build(), TorrentCategory.SERIAL),
    TG_OPERATION_SYSTEM(InlineKeyboardButton.builder().text(SystemTorrentType.OPERATION_SYSTEM.getDescription()).callbackData("TG_" + SystemTorrentType.OPERATION_SYSTEM.name()).build(), TorrentCategory.OPERATION_SYSTEM),
    TG_OTHER(InlineKeyboardButton.builder().text(SystemTorrentType.OTHER.getDescription()).callbackData("TG_" + SystemTorrentType.OTHER.name()).build(), TorrentCategory.OTHER),
    ;

    TorrentFileResolverButtonData(InlineKeyboardButton button, TorrentCategory torrentCategory) {
        this.button = button;
        this.torrentCategory = torrentCategory;
    }

    private final InlineKeyboardButton button;
    private final TorrentCategory torrentCategory;
    public static final String SUFFIX_BUTTON_NAME = "TG_";

}
