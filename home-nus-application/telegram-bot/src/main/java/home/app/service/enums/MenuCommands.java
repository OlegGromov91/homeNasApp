package home.app.service.enums;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@Getter
public enum MenuCommands {
    TORRENT_MENU_COMMAND("/torrent", "Торрент качалка", new BotCommand("/torrent", "Торрент качалка")),
    TEST("/test", "Кнопка отладки", new BotCommand("/test", "Кнопка отладки")),
    ;

    private final String command;
    private final String description;
    private final BotCommand botCommand;
    public static final String TYPE = "bot_command";


    MenuCommands(String command, String description, BotCommand botCommand) {
        this.command = command;
        this.description = description;
        this.botCommand = botCommand;
    }
}
