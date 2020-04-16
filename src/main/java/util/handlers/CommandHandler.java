package util.handlers;

import commands.CommandResponse;
import util.actions.FileSystemAction;

/**
 * A class to accept and execute commands
 */
public class CommandHandler implements FileSystemActionHandler {
    @Override
    public CommandResponse handle(FileSystemAction command) {
        return (CommandResponse) command.execute();
    }
}
