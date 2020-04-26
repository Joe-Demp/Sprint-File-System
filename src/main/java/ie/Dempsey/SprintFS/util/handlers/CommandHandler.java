package ie.Dempsey.SprintFS.util.handlers;


import ie.Dempsey.SprintFS.commands.CommandResponse;
import ie.Dempsey.SprintFS.util.actions.FileSystemAction;

/**
 * A class to accept and execute commands
 */
public class CommandHandler implements FileSystemActionHandler {
    @Override
    public CommandResponse handle(FileSystemAction command) {
        return (CommandResponse) command.execute();
    }
}
