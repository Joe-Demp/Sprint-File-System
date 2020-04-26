package ie.Dempsey.SprintFS.exceptions;

public class NonExistentFileException extends RuntimeException {
    public NonExistentFileException() {
        super();
    }

    public NonExistentFileException(String message) {
        super(message);
    }
}
