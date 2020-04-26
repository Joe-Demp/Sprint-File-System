package ie.Dempsey.SprintFS.queries;

import ie.Dempsey.SprintFS.util.actions.FileSystemAction;

/**
 * A template for actions that gather information about the file system
 */
public interface Query extends FileSystemAction {
    QueryResponse execute();
}
