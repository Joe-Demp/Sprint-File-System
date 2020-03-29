package queries;

import util.FileSystemAction;

/**
 * TODO: 26/03/2020  Write the javadoc for Query
 */
public interface Query extends FileSystemAction {
    QueryResponse execute();
}
