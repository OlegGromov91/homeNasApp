package home.app.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBotUtil {

    public static final int INITIAL_MAX_BUTTON_SIZE_ON_ROW = 3;

    public InlineKeyboardButton buildButton(String text, String callbackButtonSuffix) {
        return InlineKeyboardButton.builder().text(text).callbackData(callbackButtonSuffix + text).build();
    }

    public List<List<InlineKeyboardButton>> calculateRows(List<InlineKeyboardButton> buttons) {
        return calculateRows(INITIAL_MAX_BUTTON_SIZE_ON_ROW, buttons);
    }

    public List<List<InlineKeyboardButton>> calculateRows(int maxButtonOnRow, List<InlineKeyboardButton> buttons) {
        int row = maxButtonOnRow;
        int fullRows = buttons.size() / maxButtonOnRow;
        int iterator = 0;
        int remainder = buttons.size() - maxButtonOnRow * fullRows;
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();

        for (int i = 0; i < fullRows; i++) {
            buttonRows.add(buttons.subList(iterator, row));
            iterator += maxButtonOnRow;
            row += maxButtonOnRow;
        }
        if (remainder != 0) {
            buttonRows.add(buttons.subList(buttons.size() - remainder, buttons.size()));
        }
        return buttonRows;
    }
}
