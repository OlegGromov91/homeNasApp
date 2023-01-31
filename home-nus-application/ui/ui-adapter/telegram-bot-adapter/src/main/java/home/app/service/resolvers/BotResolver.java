package home.app.service.resolvers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public interface BotResolver {

    BotApiMethod<? extends Serializable> resolve(Update update);

    Class<? extends BotResolver> type();

    /**
     * Метод должен однозначно определить подходит ли резолвер для данных, приходящих с телеграмма
     *
     * @param update
     * @return
     */
    boolean identifyResolver(Update update);

}
