package home.app.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value(value = "${bot.name}")
    private String botName;

    @Value(value = "${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Autowired
    private FileStorage fileStorage;

    @Override
    public void onUpdateReceived(Update update) {

        fileStorage.processDoc(update.getMessage());
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
