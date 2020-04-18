package commands;

import java.io.File;

public abstract class AbstractCommand implements Command {
    protected final File file;

    public AbstractCommand(File file) {
        this.file = file;
    }

    @Override
    public CommandResponse execute() {
        boolean commandSuccess = triggerAction();
        return makeResponse(commandSuccess);
    }

    /**
     * Does the action that this {@code Command} was built to carry out
     *
     * @return true if the action was carried out successfully, false otherwise
     */
    protected abstract boolean triggerAction();

    /**
     * Creates a response, with a contextual message, for a successful or failed {@code Command}
     */
    protected abstract CommandResponse makeResponse(boolean success);
}
