package home.app.service.resolvers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotResolver {

    void resolve(Update update);

    Class<? extends BotResolver> type();

    boolean identifyResolver(Message tgMessage);
}
