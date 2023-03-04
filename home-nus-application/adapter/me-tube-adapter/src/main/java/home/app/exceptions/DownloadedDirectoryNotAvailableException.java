package home.app.exceptions;

public class DownloadedDirectoryNotAvailableException extends RuntimeException {

    public DownloadedDirectoryNotAvailableException() {
        super();
    }

    public DownloadedDirectoryNotAvailableException(String message) {
        super(message);
    }

    public DownloadedDirectoryNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadedDirectoryNotAvailableException(Throwable cause) {
        super(cause);
    }

    protected DownloadedDirectoryNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
