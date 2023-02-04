package home.app.service.resolvers.menu;

import home.app.service.enums.MenuCommands;
import home.app.service.resolvers.AbstractBotResolver;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;


public abstract class MenuBotResolver extends AbstractBotResolver {

    @Override
    public boolean identifyResolver(Update update) {
        if (update.hasMessage() && update.getMessage().getEntities().size() == 1) {
            MessageEntity entity = update.getMessage().getEntities().get(0);
            return entity.getType().equals(MenuCommands.TYPE) && identifyCommand(update);
        }
        return false;
    }

    protected abstract boolean identifyCommand(Update update);

}
