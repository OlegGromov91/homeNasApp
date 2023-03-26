package home.app.service.resolvers;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractMarkupAbleBotResolver implements BotResolver {

    private static final int INITIAL_MAX_BUTTON_SIZE_ON_ROW = 3;

    protected InlineKeyboardMarkup buildKeyboardKeyboardMarkup(Map<String, String> textAndCallBackData) {
        return buildKeyboardKeyboardMarkup(INITIAL_MAX_BUTTON_SIZE_ON_ROW, textAndCallBackData);

    }

    protected InlineKeyboardMarkup buildKeyboardKeyboardMarkup(int maxButtonOnRow, Map<String, String> textAndCallBackData) {

        List<InlineKeyboardButton> buttons = textAndCallBackData.entrySet().stream()
                .map(entry -> InlineKeyboardButton.builder()
                        .text(entry.getKey())
                        .callbackData(entry.getValue())
                        .build())
                .collect(Collectors.toList());

        return InlineKeyboardMarkup.builder()
                .keyboard(
                        calculateRows(maxButtonOnRow, buttons)
                ).build();

    }

    private List<List<InlineKeyboardButton>> calculateRows(int maxButtonOnRow, List<InlineKeyboardButton> buttons) {
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
