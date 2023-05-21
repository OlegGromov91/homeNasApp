package home.app.service.resolvers.menu.torrent;

import com.google.common.base.Strings;
import home.app.service.enums.TorrentMenuResolverButtonData;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class DeleteTorrentMenuBotResolver extends ActionTorrentMenuBotResolver {

    private static final String DELETE_QUESTION = "Какой именно торрент вы хотели бы удалить? \n (!!!ПРЕДУПРЕЖДЕНИЕ: Так же удалятся загруженные файлы)";

    @Override
    public boolean identifyCallBackResolver(Update update) {
        if (update.hasCallbackQuery() && !Strings.isNullOrEmpty(update.getCallbackQuery().getData())) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String buttonData = callbackQuery.getData();
            try {
                return Objects.equals(TorrentMenuResolverButtonData.TG_MENU_DELETE.name(), buttonData);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return false;
    }

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        return getPreFilledCallbackMessage(callbackQuery)
                .text(DELETE_QUESTION)
                .replyMarkup(buildKeyboardFromTorrents(DELETE_ACTION))
                .build();
    }


}
