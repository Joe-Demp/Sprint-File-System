package util;

import java.io.File;
import java.time.Instant;

/**
 * TODO 26/03/2020  Write the javadoc for FileSystemActionResponse
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

    public FileSystemActionResponse(boolean success, File file, String message, String indicator) {
        this.success = success;
        this.file = file;
        this.message = String.format(Format, Instant.now(), file.toString(), indicator, message);
    }

    /**
     * @return
     */
    public boolean success() {
        return success;
    }

    /**
     * @return
     */
    public File file() {
        return file;
    }

    /**
     * @return
     */
    public String message() {
        return message;
    }
}
