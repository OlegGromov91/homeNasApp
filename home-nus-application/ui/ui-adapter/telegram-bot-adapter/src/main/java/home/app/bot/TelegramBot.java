package home.app.bot;

import home.app.service.BotResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value(value = "${bot.name}")
    private String botName;
    @Value(value = "${bot.token}")
    private String botToken;
    @Autowired
    private BotResolverService botResolverService;
    @Autowired
    private SetMyCommands menuCommands;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(menuCommands);
            execute(botResolverService.resolve(update));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
