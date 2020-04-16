package util.handlers;

import queries.QueryResponse;
import util.actions.FileSystemAction;

/**
 * A class to execute Query objects and return their QueryResponses
 */
public class QueryHandler implements FileSystemActionHandler {
    @Override
    public QueryResponse handle(FileSystemAction query) {
        return (QueryResponse) query.execute();
    }
}
