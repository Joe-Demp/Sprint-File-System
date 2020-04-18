package commands;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

public class MoveCommand extends AbstractCommand {
    public MoveCommand(File file) {
        super(file);
    }

    @Override
    protected boolean triggerAction() {
        throw new NotImplementedException();
    }

    @Override
    protected CommandResponse makeResponse(boolean success) {
        throw new NotImplementedException();
    }
}
