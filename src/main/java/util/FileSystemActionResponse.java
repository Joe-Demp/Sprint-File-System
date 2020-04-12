package util;

import java.io.File;
import java.time.Instant;

/**
 * A DTO specifying the results of a {@code FileSystemAction}
 */
public abstract class FileSystemActionResponse {
    /**
     * Format string for message.<br>
     * Arguments are: current time, filename, success/fail indicator and message
     */
    public static final String Format = "%s -- %s - %s: %s";
    private boolean success;
    private File file;
    private String message;

    /**
     * A base constructor for building {@code FileSystemActionResponse} objects easily
     *
     * @param success   true if the action that the {@code FileSystemAction} was built to execute did so successfully,
     *                  false otherwise
     * @param file      the file that the associated {@code FileSystemAction} was set to act on
     * @param message   an explanation of this {@code FileSystemActionResponse}
     * @param indicator a brief standardized message to indicate success or failure,
     *                  useful for rendering this object as a {@code String}.
     *                  This is different from 'message' because it should not contain any information that identifies
     *                  the {@code FileSystemAction} that generated it, apart from its success or failure
     */
    public FileSystemActionResponse(boolean success, File file, String message, String indicator) {
        this.success = success;
        this.file = file;
        this.message = String.format(Format, Instant.now(), file.toString(), indicator, message);
    }

    /**
     * @return true if the {@code FileSystemAction} that generated this response executed correctly
     * with a positive result, otherwise false
     */
    public boolean success() {
        return success;
    }

    /**
     * @return the {@code File} object that the {@code FileSystemAction} that generated this response was to act on
     */
    public File file() {
        return file;
    }

    /**
     * @return the message associated with this response. This should usually identify success or failure to the client
     * in a human readable format, and may include information gathered from the other fields in this class
     */
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return message();
    }
}
