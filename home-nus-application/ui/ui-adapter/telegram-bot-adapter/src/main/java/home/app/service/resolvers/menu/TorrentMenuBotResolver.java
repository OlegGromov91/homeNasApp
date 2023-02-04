package home.app.service.resolvers.menu;

import com.google.common.base.Strings;
import home.app.service.QbTorrentService;
import home.app.service.enums.MenuCommands;
import home.app.service.enums.TorrentMenuResolverButtonData;
import home.app.service.resolvers.BotResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Objects;

@Component
public class TorrentMenuBotResolver extends MenuBotResolver {

    private static final String COMMAND = MenuCommands.TORRENT_MENU_COMMAND.getCommand();
    private static final String MENU_MESSAGE = "Выберит, что вы хотели бы сделать";

    @Autowired
    private InlineKeyboardMarkup torrentMenuInlineKeyboardMarkup;
    @Autowired
    private QbTorrentService qbTorrentService;

    @Override
    public Class<? extends BotResolver> type() {
        return this.getClass();
    }

    @Override
    public boolean identifyCallBackResolver(Update update) {
        if (update.hasCallbackQuery() && !Strings.isNullOrEmpty(update.getCallbackQuery().getData())) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String buttonData = callbackQuery.getData();
            try {
                TorrentMenuResolverButtonData.valueOf(buttonData);
                return true;
            } catch (IllegalArgumentException ignored) {
            }
        }
        return false;
    }

    @Override
    protected boolean identifyCommand(Update update) {
        return Objects.equals(update.getMessage().getEntities().get(0).getText(), COMMAND);
    }


    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        EditMessageText message = new EditMessageText();
        message.setText("COOL");
        message.setChatId(chatId);
        message.setMessageId(messageId);
        return message;
    }

    @Override
    protected SendMessage processMessage(Message telegramMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(telegramMessage.getChatId());
        message.setText(MENU_MESSAGE);
        message.setReplyMarkup(torrentMenuInlineKeyboardMarkup);
        return message;
    }
}
