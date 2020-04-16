package util.handlers;

import util.actions.FileSystemActionResponse;

/**
 * A handler to take FileSystemActionResponses and do something with them
 */
public interface ResponseHandler {
    /**
     * @param response
     */
    void handle(FileSystemActionResponse response);
}
