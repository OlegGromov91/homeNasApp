package home.app.service.resolvers.url;

import home.app.service.resolvers.AbstractBotResolver;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractUrlResolver extends AbstractBotResolver {

    protected final static String TYPE = "url";

    @Override
    public boolean identifyResolver(Update update) {
        return update.hasMessage() && update.getMessage().hasEntities() &&
                update.getMessage().getEntities().stream().anyMatch(messageEntity -> messageEntity.getType().equals(TYPE));
    }
}
