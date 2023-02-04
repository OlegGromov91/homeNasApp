package home.app.service.resolvers.file;

import home.app.service.resolvers.AbstractBotResolver;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class FileBotResolver extends AbstractBotResolver {

    /**
     * Добавлять сюда другие типы файлов под типу message.hasDocument();
     *
     * @param update
     * @return
     */
    @Override
    public boolean identifyResolver(Update update) {
        return update.hasMessage() && (update.getMessage().hasDocument() || update.getMessage().hasAudio());
    }

    protected abstract boolean identifyFileType(Update update);


}
