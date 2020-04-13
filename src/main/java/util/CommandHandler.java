package util;

import commands.CommandResponse;

/**
 * TODO fill in
 */
public class CommandHandler implements FileSystemActionHandler {
    @Override
    public CommandResponse handle(FileSystemAction command) {
        return (CommandResponse) command.execute();
    }
}
