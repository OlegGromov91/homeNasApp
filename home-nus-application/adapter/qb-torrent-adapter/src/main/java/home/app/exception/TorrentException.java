package home.app.exception;

public class TorrentException extends RuntimeException {

    public TorrentException() {
        super();
    }

    public TorrentException(String message) {
        super(message);
    }

    public TorrentException(String message, Throwable cause) {
        super(message, cause);
    }

    public TorrentException(Throwable cause) {
        super(cause);
    }

    protected TorrentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
