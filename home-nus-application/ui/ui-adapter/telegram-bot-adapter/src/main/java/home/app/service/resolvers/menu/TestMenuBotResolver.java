package home.app.service.resolvers.menu;

import com.google.common.base.Strings;
import home.app.model.qbTorrent.enums.SystemTorrentType;
import home.app.service.enums.MenuCommands;
import home.app.service.resolvers.BotResolver;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class TestMenuBotResolver extends MenuBotResolver {

    private static final String COMMAND = MenuCommands.TEST_MENU_COMMAND.getCommand();


    @Override
    protected boolean identifyCommand(Update update) {
        System.out.println();
        return Objects.equals(update.getMessage().getEntities().get(0).getText(), COMMAND);
    }


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
                SystemTorrentType.valueOf(buttonData);
                return true;
            } catch (IllegalArgumentException ignored) {
            }
        }
        return false;
    }

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    protected SendMessage processMessage(Message telegramMessage) {
        return null;
    }
}
