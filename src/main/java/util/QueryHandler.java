package util;

import queries.QueryResponse;

/**
 * A class to execute Query objects and return their QueryResponses
 */
public class QueryHandler implements FileSystemActionHandler {
    @Override
    public QueryResponse handle(FileSystemAction query) {
        return (QueryResponse) query.execute();
    }
}
