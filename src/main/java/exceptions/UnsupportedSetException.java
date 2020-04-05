package exceptions;

/**
 * To be thrown whenever an inappropriate set operation is done<br>
 * e.g. when isDirectoryAction is set to true for a FileQuery
 */
public class UnsupportedSetException extends Exception {
    public UnsupportedSetException(String message) {
        super(message);
    }

    public UnsupportedSetException() {
        super();
    }
}
