package home.app.service.resolvers.menu;

import home.app.service.enums.MenuCommands;
import home.app.service.resolvers.BotResolver;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Objects;

public class TorrentMenuBotResolver extends MenuBotResolver {

    private static final String COMMAND = MenuCommands.TORRENT_MENU_COMMAND.getCommand();

    @Override
    public BotApiMethod<? extends Serializable> resolve(Update update) {
        return null;
    }

    @Override
    public Class<? extends BotResolver> type() {
        return this.getClass();
    }

    @Override
    public boolean identifyResolver(Update update) {
        return (super.identifyResolver(update)) && identifyCommand(update);
    }

    @Override
    public boolean identifyCallBackResolver(Update update) {
        return false;
    }

    @Override
    protected boolean identifyCommand(Update update) {
        return Objects.equals(update.getMessage().getEntities().get(0).getText(), COMMAND);
    }


}
