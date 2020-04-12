package queries;

import util.FileSystemAction;

/**
 * A template for actions that gather information about the file system
 */
public interface Query extends FileSystemAction {
    QueryResponse execute();
}
