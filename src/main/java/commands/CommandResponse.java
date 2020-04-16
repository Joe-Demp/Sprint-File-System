package commands;

import util.actions.FileSystemActionResponse;

import java.io.File;

/**
 * A utility class to provide for easy construction of responses issued by {@code Command} objects.
 */
public abstract class CommandResponse extends FileSystemActionResponse {
    /**
     * @param success   true if the action that the {@code Command} was built to execute did so successfully,
     *                  false otherwise
     * @param file      the file that the associated {@code Command} was set to act on
     * @param message   an explanation of this {@code CommandResponse}
     * @param indicator a brief message to indicate success or failure, useful for rendering this object as a
     *                  {@code String}
     */
    public CommandResponse(boolean success, File file, String message, String indicator) {
        super(success, file, message, indicator);
    }
}
