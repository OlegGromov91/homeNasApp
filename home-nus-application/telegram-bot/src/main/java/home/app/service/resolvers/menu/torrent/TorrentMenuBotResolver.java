package home.app.service.resolvers.menu.torrent;

import home.app.service.enums.MenuCommands;
import home.app.service.resolvers.MessageAble;
import home.app.service.resolvers.menu.MenuBotResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Objects;


public abstract class TorrentMenuBotResolver extends MenuBotResolver implements MessageAble {

    private static final String COMMAND = MenuCommands.TORRENT_MENU_COMMAND.getCommand();
    private static final String MENU_MESSAGE = "Выберит, что вы хотели бы сделать";

    @Autowired
    private InlineKeyboardMarkup torrentMenuInlineKeyboardMarkup;

    @Override
    protected boolean identifyCommand(Update update) {
        return Objects.equals(update.getMessage().getEntities().get(0).getText(), COMMAND);
    }

    @Override
    protected SendMessage processMessage(Message telegramMessage) {
        return getPreFilledMessage(telegramMessage)
                .text(MENU_MESSAGE)
                .replyMarkup(torrentMenuInlineKeyboardMarkup)
                .build();
    }
}
