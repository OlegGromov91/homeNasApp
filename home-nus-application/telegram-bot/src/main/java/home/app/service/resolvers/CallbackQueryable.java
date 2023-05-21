package home.app.service.resolvers;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryable {

    default Long extractChatIdFromCallbackQuery(CallbackQuery callbackQuery) {
        return callbackQuery.getMessage().getChatId();
    }

    default Integer extractMessageIdFromCallbackQuery(CallbackQuery callbackQuery) {
        return callbackQuery.getMessage().getMessageId();
    }

    default EditMessageText.EditMessageTextBuilder getPreFilledCallbackMessage(CallbackQuery callbackQuery) {
        return EditMessageText.builder()
                .chatId(extractChatIdFromCallbackQuery(callbackQuery))
                .messageId(extractMessageIdFromCallbackQuery(callbackQuery));
    }
}
