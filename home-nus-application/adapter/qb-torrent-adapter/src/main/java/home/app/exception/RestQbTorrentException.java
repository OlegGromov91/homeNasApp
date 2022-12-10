package home.app.exception;

public class RestQbTorrentException extends RuntimeException {

    public RestQbTorrentException() {
        super();
    }

    public RestQbTorrentException(String message) {
        super(message);
    }

    public RestQbTorrentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestQbTorrentException(Throwable cause) {
        super(cause);
    }

    protected RestQbTorrentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
