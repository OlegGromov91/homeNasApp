package home.app.service.resolvers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotResolver {

    SendMessage resolve(Update update);

    Class<? extends BotResolver> type();

    /**
     * Метод должен однозначно определить подходит ли резолвер для данных, приходящих с телеграмма
     *
     * @param update
     * @return
     */
    boolean identifyResolver(Update update);

}
