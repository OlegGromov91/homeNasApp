package home.app.service.resolvers.menu.torrent;

import com.google.common.base.Strings;
import home.app.service.resolvers.CallbackQueryable;
import home.app.view.qbTorrent.TorrentView;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ActionTorrentMenuBotResolver extends TorrentMenuBotResolver implements CallbackQueryable {

    protected static final String CALLBACK_DATA = "TORRENT_ACTION_";
    protected static final String DELETE_ACTION = "DELETE_";
    protected static final String PAUSE_ACTION = "PAUSE_";
    protected static final String RESUME_ACTION = "RESUME_";

    @Override
    public boolean identifyCallBackResolver(Update update) {
        if (update.hasCallbackQuery() && !Strings.isNullOrEmpty(update.getCallbackQuery().getData())) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String buttonData = callbackQuery.getData();
            return Objects.nonNull(buttonData) && buttonData.contains(CALLBACK_DATA);
        }
        return false;
    }

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        String data = action(callbackQuery);
        return getPreFilledCallbackMessage(callbackQuery)
                .text(data)
                .build();
    }

    //TODO идем в базу и берем торренты оттуда
    protected InlineKeyboardMarkup buildKeyboardFromTorrents(String action) {

        List<TorrentView> torrentDataViews = qbTorrentService.getInfoAboutAllDownloadingTorrents();

        return buildKeyboardKeyboardMarkup(1,
                torrentDataViews.stream().collect(Collectors.toMap(TorrentView::getName, torrentView -> CALLBACK_DATA + action + torrentView.getHash())));
    }

    private String action(CallbackQuery callbackQuery) {
        String buttonData = callbackQuery.getData();
        if (buttonData.contains(PAUSE_ACTION)) {
            qbTorrentService.pauseTorrent(extractTorrentHash(buttonData, PAUSE_ACTION));
            return "Торрент успешно поставлен на паузу";
        } else if (buttonData.contains(RESUME_ACTION)) {
            qbTorrentService.resumeTorrent(extractTorrentHash(buttonData, RESUME_ACTION));
            return "Торрент снова загружается";
        } else if (buttonData.contains(DELETE_ACTION)) {
            qbTorrentService.deleteTorrentAndData(extractTorrentHash(buttonData, DELETE_ACTION));
            return "Торрент и скаченные файлы успешно удалены";
        }
        throw new UnsupportedOperationException("Cannot find operation for data " + buttonData);
    }

    private String extractTorrentHash(String buttonData, String action) {
        return buttonData.substring(CALLBACK_DATA.length() + action.length());
    }

}
