package ie.Dempsey.SprintFS.exceptions;

public class BadCommandException extends RuntimeException {
    public BadCommandException() {
        super();
    }

    public BadCommandException(String message) {
        super(message);
    }
}
