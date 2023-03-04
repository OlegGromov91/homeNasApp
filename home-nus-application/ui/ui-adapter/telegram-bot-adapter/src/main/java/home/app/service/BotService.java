package home.app.service;

import home.app.exceptions.BotResolveException;
import home.app.service.resolvers.BotResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
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

            BotResolver botResolver = resolvers.stream()
                    .filter(resolver -> resolver.identifyResolver(update) || resolver.identifyCallBackResolver(update))
                    .findFirst().orElseThrow(() -> new BotResolveException("Can not find resolver"));
            return botResolver.resolve(update);

    }


}
