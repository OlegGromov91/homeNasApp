package home.app.configuration;

import home.app.service.enums.TorrentButtonData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
public class BotConfiguration {

    @Bean(value = "torrentInlineKeyboardMarkup")
    public InlineKeyboardMarkup inlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(
                List.of(Arrays.stream(TorrentButtonData.values()).map(TorrentButtonData::getButton).collect(Collectors.toList()))
        );
        return inlineKeyboardMarkup;
    }

    @Bean()
    @Scope(SCOPE_PROTOTYPE)
    public SendMessage sendMessage() {
        return new SendMessage();
    }

}
