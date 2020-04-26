package ie.Dempsey.SprintFS.commands;

import java.io.File;

public class DeleteCommand extends AbstractCommand {
    public DeleteCommand(File file) {
        super(file);
    }

    @Override
    protected boolean triggerAction() {
        return file.delete();
    }

    @Override
    protected CommandResponse makeResponse(boolean success) {
        String message = success ? "deleted" : "not deleted";
        return new CommandResponse(success, file, message);
    }
}
