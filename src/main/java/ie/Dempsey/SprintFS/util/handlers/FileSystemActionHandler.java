package ie.Dempsey.SprintFS.util.handlers;


import ie.Dempsey.SprintFS.util.actions.FileSystemAction;
import ie.Dempsey.SprintFS.util.actions.FileSystemActionResponse;

/**
 * A contract to enforce all handlers across the project to specify a method handle, to execute {@code FileSystemAction}
 * objects and return {@code FileSystemActionResponse} objects.
 * <p>
 * This supports the CQRS design pattern.
 * </p>
 */
public interface FileSystemActionHandler {
    /**
     * @param action the procedure to be executed
     * @return the response issued by action after execution
     */
    FileSystemActionResponse handle(FileSystemAction action);
}
