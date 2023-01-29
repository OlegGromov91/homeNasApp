package home.app.service.exceptions;

public class BotResolveException extends RuntimeException {

    public BotResolveException() {
        super();
    }

    public BotResolveException(String message) {
        super(message);
    }

    public BotResolveException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotResolveException(Throwable cause) {
        super(cause);
    }

    protected BotResolveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
