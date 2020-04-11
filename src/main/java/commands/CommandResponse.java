package commands;

import util.FileSystemActionResponse;

import java.io.File;

/**
 * TODO: 26/03/2020 Write the javadoc for Command Response
 */
public abstract class CommandResponse extends FileSystemActionResponse {
    public CommandResponse(boolean success, File file, String message, String indicator) {
        super(success, file, message, indicator);
    }
}
