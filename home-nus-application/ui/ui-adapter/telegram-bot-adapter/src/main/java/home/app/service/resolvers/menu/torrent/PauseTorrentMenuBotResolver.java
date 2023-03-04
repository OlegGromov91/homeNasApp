package home.app.service.resolvers.menu.torrent;

import com.google.common.base.Strings;
import home.app.service.enums.TorrentMenuResolverButtonData;
import home.app.service.resolvers.BotResolver;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class PauseTorrentMenuBotResolver extends ActionTorrentMenuBotResolver {

    @Override
    public Class<? extends BotResolver> type() {
        return this.getClass();
    }

    @Override
    public boolean identifyCallBackResolver(Update update) {
        if (update.hasCallbackQuery() && !Strings.isNullOrEmpty(update.getCallbackQuery().getData())) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String buttonData = callbackQuery.getData();
            try {
                return Objects.equals(TorrentMenuResolverButtonData.TG_MENU_PAUSE.name(), buttonData);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return false;
    }

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {

        String data = "Какой именно торрент вы хотели бы остановить?";

        Long chatId = extractChatIdFromCallbackQuery(callbackQuery);
        Integer messageId = callbackQuery.getMessage().getMessageId();
        EditMessageText message = new EditMessageText();
        message.setText(data);
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setReplyMarkup(buildKeyboardFromTorrents(PAUSE_ACTION));
        return message;
    }

}