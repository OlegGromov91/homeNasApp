package home.app.service.resolvers.url;

import home.app.service.MeTubeService;
import home.app.service.resolvers.BotResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class YouTubeResolver extends AbstractUrlResolver {

    private static final String BASIC_SITE_URL = "https://www.youtube.com/";

    @Autowired
    private MeTubeService meTubeService;

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }

    //TODO заполнять формат динамически
    @Override
    protected SendMessage processMessage(Message telegramMessage) {
        String url = telegramMessage.getEntities().stream()
                .filter(messageEntity -> messageEntity.getText().contains(BASIC_SITE_URL))
                .map(MessageEntity::getText)
                .findFirst().get();

        meTubeService.addNewVideoToDownload(url, "mp4");
        Long chatId = telegramMessage.getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Видео загружается");
        return message;
    }

    @Override
    public Class<? extends BotResolver> type() {
        return null;
    }


    // TODO: сделать цепочку от родителя к потомка с hasSome...
    @Override
    public boolean identifyResolver(Update update) {
        return super.identifyResolver(update) && update.getMessage().getEntities().stream().anyMatch(messageEntity -> messageEntity.getText().contains(BASIC_SITE_URL));
    }

    @Override
    public boolean identifyCallBackResolver(Update update) {
        return false;
    }
}
