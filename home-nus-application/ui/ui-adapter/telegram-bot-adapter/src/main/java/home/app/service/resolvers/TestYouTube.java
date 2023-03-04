package home.app.service.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import home.app.service.rest.RestMeTubeService;

@Component
public class TestYouTube extends AbstractBotResolver{

    @Autowired
    private RestMeTubeService restMeTubeService;
    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    protected SendMessage processMessage(Message telegramMessage) {
        restMeTubeService.addVideoToDownload("sadadsa");
        Long chatId = telegramMessage.getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(restMeTubeService.hashCode() + "");
        return message;
    }

    @Override
    public Class<? extends BotResolver> type() {
        return null;
    }

    @Override
    public boolean identifyResolver(Update update) {
        return update.getMessage().getEntities().stream().anyMatch(i -> i.getText().contains("youtube")) ||  update.getMessage().getText().contains("youtube");
    }

    @Override
    public boolean identifyCallBackResolver(Update update) {
        return false;
    }
}
