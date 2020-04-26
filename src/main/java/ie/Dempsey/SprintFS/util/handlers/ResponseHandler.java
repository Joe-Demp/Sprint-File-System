package ie.Dempsey.SprintFS.util.handlers;


import ie.Dempsey.SprintFS.util.actions.FileSystemActionResponse;

/**
 * A handler to take FileSystemActionResponses and do something with them
 */
public interface ResponseHandler {
    /**
     * @param response
     */
    void handle(FileSystemActionResponse response);
}
