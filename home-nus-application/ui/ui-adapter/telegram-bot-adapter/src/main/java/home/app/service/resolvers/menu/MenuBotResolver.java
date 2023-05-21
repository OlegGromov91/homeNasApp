package home.app.service.resolvers.menu;

import home.app.service.enums.MenuCommands;
import home.app.service.resolvers.AbstractBotResolver;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;


public abstract class MenuBotResolver extends AbstractBotResolver {

    @Override
    public boolean identifyMessageResolver(Update update) {
        boolean isUpdateHasOneEntity = update.hasMessage() && update.getMessage().hasEntities() && update.getMessage().getEntities().size() == 1;
        if (isUpdateHasOneEntity) {
            MessageEntity entity = update.getMessage().getEntities().get(0);
            return entity.getType().equals(MenuCommands.TYPE) && identifyCommand(update);
        }
        return false;
    }

    protected abstract boolean identifyCommand(Update update);

}
