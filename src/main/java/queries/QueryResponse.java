package queries;

import util.FileSystemActionResponse;

import java.io.File;

/**
 * TODO 26/03/2020  Write the javadoc for QueryResponse
 */
public class QueryResponse extends FileSystemActionResponse {
    public QueryResponse(boolean success, File file, String message, String indicator) {
        super(success, file, message, indicator);
    }
}
