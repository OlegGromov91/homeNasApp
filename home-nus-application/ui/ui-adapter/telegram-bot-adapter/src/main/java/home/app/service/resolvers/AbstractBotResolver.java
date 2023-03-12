package home.app.service.resolvers;

import home.app.service.QbTorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public abstract class AbstractBotResolver implements BotResolver {

    @Autowired
    protected QbTorrentService qbTorrentService;

    @Override
    @Transactional
    public BotApiMethod<? extends Serializable> resolve(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            return processMessage(message);
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return processCallbackQuery(callbackQuery);
        }
        throw new UnsupportedOperationException("can not find resolver");
    }

    protected abstract EditMessageText processCallbackQuery(CallbackQuery callbackQuery);

    protected abstract SendMessage processMessage(Message telegramMessage);

    protected Long extractChatIdFromCallbackQuery(CallbackQuery callbackQuery) {
        return callbackQuery.getMessage().getChatId();
    }
}
