package home.app.service;

import home.app.service.exceptions.BotResolveException;
import home.app.service.resolvers.BotResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class BotService {

    @Autowired
    @Lazy
    private List<BotResolver> resolvers = new ArrayList<>();

    public BotApiMethod<? extends Serializable> resolve(Update update) {
        try {
            BotResolver botResolver = resolvers.stream()
                    .filter(resolver -> resolver.identifyResolver(update))
                    .findFirst().orElseThrow(() -> new BotResolveException("Can not find resolver"));
            return botResolver.resolve(update);
        } catch (BotResolveException e) {
            e.printStackTrace();
        }
        SendMessage message = new SendMessage();

        message.setChatId(update.getMessage().getChatId());

        message.setText("сообщение дошло до бота, но что-то пошло не так :)");
        return message;
    }


}
