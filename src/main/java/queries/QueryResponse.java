package queries;

import util.FileSystemActionResponse;

import java.io.File;

/**
 * A utility class to provide for easy construction of responses issued by {@code Query} objects.
 */
public abstract class QueryResponse extends FileSystemActionResponse {
    /**
     * @param success   true if the {@code Query} executed successfully, false otherwise
     * @param file      the file that the associated {@code Query} was set to act on
     * @param message   an explanation of this {@code QueryResponse}
     * @param indicator a brief message to indicate success or failure, useful for rendering this object as a
     *                  {@code String}
     */
    public QueryResponse(boolean success, File file, String message, String indicator) {
        super(success, file, message, indicator);
    }
}
