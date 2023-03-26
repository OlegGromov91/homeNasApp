package home.app.service.resolvers.menu.torrent;

import com.google.common.base.Strings;
import home.app.service.resolvers.CallbackQueryable;
import home.app.view.qbTorrent.TorrentView;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(
                torrentDataViews.stream().map(torrent -> List.of(
                        InlineKeyboardButton.builder().text(torrent.getName()).callbackData(CALLBACK_DATA + action + torrent.getHash()).build()
                        )
                ).collect(Collectors.toList())
        );
        return inlineKeyboardMarkup;
    }

    private String action(CallbackQuery callbackQuery) {
        String buttonData = callbackQuery.getData();
        String torrentHash = null;
        if (buttonData.contains(PAUSE_ACTION)) {
            torrentHash = extractTorrentHash(buttonData, PAUSE_ACTION);
            qbTorrentService.pauseTorrent(torrentHash);
            return "Торрент успешно поставлен на паузу";
        } else if (buttonData.contains(RESUME_ACTION)) {
            torrentHash = extractTorrentHash(buttonData, RESUME_ACTION);
            qbTorrentService.resumeTorrent(torrentHash);
            return "Торрент снова загружается";
        } else if (buttonData.contains(DELETE_ACTION)) {
            torrentHash = extractTorrentHash(buttonData, DELETE_ACTION);
            qbTorrentService.deleteTorrentAndData(torrentHash);
            return "Торрент и скаченные файлы успешно удалены";
        }
        throw new UnsupportedOperationException("Cannot find operation for data " + buttonData);
    }

    private String extractTorrentHash(String buttonData, String action) {
        return buttonData.substring(CALLBACK_DATA.length() + action.length());
    }

}
