package home.app.exception;

public class ExtractRawDataException extends RuntimeException {

    public ExtractRawDataException() {
        super();
    }

    public ExtractRawDataException(String message) {
        super(message);
    }

    public ExtractRawDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtractRawDataException(Throwable cause) {
        super(cause);
    }

    protected ExtractRawDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
