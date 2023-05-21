package home.app.service.resolvers.menu.torrent;

import com.google.common.base.Strings;
import home.app.service.enums.TorrentMenuResolverButtonData;
import home.app.view.qbTorrent.TorrentView;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ShowAllTorrentsMenuBotResolver extends ActionTorrentMenuBotResolver {

    private static final String TORRENT_LIST_OF_EMPTY = "Список загрузок пуст";


    @Override
    public boolean identifyCallBackResolver(Update update) {
        if (update.hasCallbackQuery() && !Strings.isNullOrEmpty(update.getCallbackQuery().getData())) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String buttonData = callbackQuery.getData();
            try {
                return Objects.equals(TorrentMenuResolverButtonData.TG_MENU_SNOW_ALL.name(), buttonData);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return false;
    }

    //TODO возможно нужно лезть в базу
    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        List<TorrentView> torrentDataViews = qbTorrentService.getInfoAboutAllDownloadingTorrents();
        String data = torrentDataViews.isEmpty() ? TORRENT_LIST_OF_EMPTY : torrentDataViews.stream().map(TorrentView::getTorrentInfo).collect(Collectors.joining());
        return getPreFilledCallbackMessage(callbackQuery)
                .text(data)
                .build();


    }

}
