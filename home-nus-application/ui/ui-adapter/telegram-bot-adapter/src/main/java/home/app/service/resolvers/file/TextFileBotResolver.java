package home.app.service.resolvers.file;

import home.app.service.resolvers.BotResolver;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

import static home.app.service.enums.FileSuffix.TXT;

@Component
public class TextFileBotResolver extends FileBotResolver {

    @Override
    public boolean identifyResolver(Update update) {
        return super.identifyResolver(update) && identifyFileType(update);
    }

    @Override
    protected boolean identifyFileType(Update update) {
        if (update.getMessage().hasDocument()) {
            Document document = update.getMessage().getDocument();
            return Objects.equals(document.getMimeType(), TXT.getMimeType()) || document.getFileName().endsWith(TXT.getSuffix());
        }
        return false;
    }

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        throw new RuntimeException("Unsuported!!!");
    }

    @Override
    protected SendMessage processMessage(Message telegramMessage) {
    throw new RuntimeException("Unsuported!!!");
    }

    @Override
    public boolean identifyCallBackResolver(Update update) {
        return false;
    }

    @Override
    public Class<? extends BotResolver> type() {
        return this.getClass();
    }
}
