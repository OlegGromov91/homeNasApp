package home.app.service.enums;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Getter
public enum TorrentMenuResolverButtonData {
    TG_MENU_SNOW_ALL(InlineKeyboardButton.builder().text("Показать загружаемые файлы").callbackData("TG_MENU_SNOW_ALL").build(), "Показать загружаемые файлы"),
    TG_MENU_PAUSE(InlineKeyboardButton.builder().text("Поставить торрент на паузу").callbackData("TG_MENU_PAUSE").build(), "Поставить торрент на паузу"),
    TG_MENU_RESUME(InlineKeyboardButton.builder().text("Возобновить закачку торрента").callbackData("TG_MENU_RESUME").build(), "Возобновить закачку торрента"),
    TG_MENU_DELETE(InlineKeyboardButton.builder().text("Удалить торрент").callbackData("TG_MENU_DELETE").build(), "Удалить торрент"),
    ;

    private final InlineKeyboardButton button;
    private final String buttonDescription;

    TorrentMenuResolverButtonData(InlineKeyboardButton button, String buttonDescription) {
        this.button = button;
        this.buttonDescription = buttonDescription;
    }
}
