package home.app.service.resolvers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageAble {

    default Long extractChatIdFromMessage(Message telegramMessage) {
        return telegramMessage.getChatId();
    }

    default SendMessage.SendMessageBuilder getPreFilledMessage(Message telegramMessage) {
        return SendMessage.builder()
                .chatId(extractChatIdFromMessage(telegramMessage));
    }
}
