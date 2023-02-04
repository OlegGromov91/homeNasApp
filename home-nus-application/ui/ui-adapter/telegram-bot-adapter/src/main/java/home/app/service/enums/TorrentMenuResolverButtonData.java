package home.app.service.enums;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Getter
public enum TorrentMenuResolverButtonData {
    TG_MENU_SNOW_ALL(InlineKeyboardButton.builder().text("Показать загружаемые файлы").callbackData("TG_MENU_SNOW_ALL").build(), "Показать загружаемые файлы"),
    ;

    private final InlineKeyboardButton button;
    private final String buttonDescription;

    TorrentMenuResolverButtonData(InlineKeyboardButton button, String buttonDescription) {
        this.button = button;
        this.buttonDescription = buttonDescription;
    }
}
