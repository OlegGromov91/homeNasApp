package home.app.exceptions;

public class ExtractNameFromYoutubeException extends RuntimeException {

    public ExtractNameFromYoutubeException() {
        super();
    }

    public ExtractNameFromYoutubeException(String message) {
        super(message);
    }

    public ExtractNameFromYoutubeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtractNameFromYoutubeException(Throwable cause) {
        super(cause);
    }

    protected ExtractNameFromYoutubeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
