package util;

import queries.Query;

import java.util.LinkedList;
import java.util.Queue;

/**
 * TODO 26/03/2020  Write javadoc for QueryHandler
 */
public class QueryHandler {
    Queue<Query> queryQueue = new LinkedList<>();

    /**
     * TODO write this
     *
     * @param handler
     */
    public QueryHandler(ResponseHandler handler) {

    }

    /**
     * Executes the given query against the file system<br>
     * and processes the result according to the QueryHandler's ResponseHandler
     *
     * @param query the query you would like executed
     */
    public void handle(Query query) {

    }
}
