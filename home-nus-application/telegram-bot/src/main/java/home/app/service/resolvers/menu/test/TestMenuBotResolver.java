package home.app.service.resolvers.menu.test;

import home.app.service.enums.MenuCommands;
import home.app.service.resolvers.MessageAble;
import home.app.service.resolvers.menu.MenuBotResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class TestMenuBotResolver extends MenuBotResolver implements MessageAble {

    private static final String COMMAND = MenuCommands.TEST.getCommand();

    @Autowired
    MeTubeJobTest meTubeJobTest;

    @Override
    protected boolean identifyCommand(Update update) {
        return Objects.equals(update.getMessage().getEntities().get(0).getText(), COMMAND);
    }

    @Override
    protected EditMessageText processCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    protected SendMessage processMessage(Message telegramMessage) {
        meTubeJobTest.schedule();
        return getPreFilledMessage(telegramMessage).text("Test").build();
    }

    @Override
    public boolean identifyCallBackResolver(Update update) {
        return false;
    }
}
