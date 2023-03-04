package home.app.exceptions;

public class RestMeTubeException extends RuntimeException {

    public RestMeTubeException() {
        super();
    }

    public RestMeTubeException(String message) {
        super(message);
    }

    public RestMeTubeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestMeTubeException(Throwable cause) {
        super(cause);
    }

    protected RestMeTubeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
