package home.app.bot;

import home.app.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value(value = "${bot.name}")
    private String botName;
    @Value(value = "${bot.token}")
    private String botToken;
    @Autowired
    private BotService botService;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        botService.resolve(update);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
