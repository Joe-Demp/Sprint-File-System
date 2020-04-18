package queries;

import util.actions.FileSystemActionResponse;

import java.io.File;

/**
 * A utility class to provide for easy construction of responses issued by {@code Query} objects.
 */
public class QueryResponse extends FileSystemActionResponse {
    /**
     * @param success true if the {@code Query} executed successfully, false otherwise
     * @param file    the file that the associated {@code Query} was set to act on
     * @param message an explanation of this {@code QueryResponse}
     */
    public QueryResponse(boolean success, File file, String message) {
        super(success, file, message, "");
    }
}
