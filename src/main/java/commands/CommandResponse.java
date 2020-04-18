package commands;

import util.actions.FileSystemActionResponse;

import java.io.File;

/**
 * A utility class to provide for easy construction of responses issued by {@code Command} objects.
 */
public class CommandResponse extends FileSystemActionResponse {
    /**
     * @param success true if the {@code Command} executed successfully, false otherwise
     * @param file    the file that the associated {@code Command} was set to act on
     * @param message an explanation of this {@code CommandResponse}
     */
    public CommandResponse(boolean success, File file, String message) {
        super(success, file, message);
    }
}
