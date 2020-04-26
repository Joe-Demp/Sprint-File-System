package ie.Dempsey.SprintFS.util.actors;



import ie.Dempsey.SprintFS.commands.Command;
import ie.Dempsey.SprintFS.commands.CommandResponse;
import ie.Dempsey.SprintFS.exceptions.BadCommandException;
import ie.Dempsey.SprintFS.util.handlers.CommandHandler;
import ie.Dempsey.SprintFS.util.handlers.ResponseHandler;
import ie.Dempsey.SprintFS.util.pipelines.FilePipeline;
import ie.Dempsey.SprintFS.util.pipelines.NullFile;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FileSystemEditor implements Runnable {
    private final boolean TWO_ARGS;
    private FilePipeline pipeline;
    private Class<? extends Command> commandClass;
    private Constructor<? extends Command> commandConstructor;
    private CommandHandler commandHandler = new CommandHandler();
    private ResponseHandler responseHandler;
    private String optional;

    public FileSystemEditor(FilePipeline pipeline,
                            Class<? extends Command> commandClass,
                            ResponseHandler responseHandler,
                            String optional) throws NoSuchMethodException {
        TWO_ARGS = true;
        this.optional = optional;
        this.pipeline = pipeline;
        this.commandClass = commandClass;
        this.commandConstructor = makeConstructor();
        this.responseHandler = responseHandler;

    }

    public FileSystemEditor(FilePipeline pipeline,
                            Class<? extends Command> commandClass,
                            ResponseHandler responseHandler) throws NoSuchMethodException {
        TWO_ARGS = false;
        this.pipeline = pipeline;
        this.commandClass = commandClass;
        this.commandConstructor = makeConstructor();
        this.responseHandler = responseHandler;
    }

    private Constructor<? extends Command> makeConstructor() throws NoSuchMethodException {
        if (TWO_ARGS) {
            return commandClass.getConstructor(File.class, String.class);
        }

        return commandClass.getConstructor(File.class);
    }

    @Override
    public void run() {
        File file = takeFile();

        while (!(file instanceof NullFile)) {
            runCommand(file);
            file = takeFile();
        }
    }

    private File takeFile() {
        try {
            return pipeline.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new NullFile();
    }

    /**
     * Takes file, constructs the specified {@code Command} and passes it to the {@code CommandHandler}.
     * Then passes the response to the {@code ResponseHandler}.
     *
     * @param file the {@code File} to execute the {@code Command} on
     */
    private void runCommand(File file) {
        Command command = makeCommand(file);
        CommandResponse response = commandHandler.handle(command);
        responseHandler.handle(response);
    }

    private Command makeCommand(File file) {
        try {
            if (TWO_ARGS) {
                return commandConstructor.newInstance(file, optional);
            } else {
                return commandConstructor.newInstance(file);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new BadCommandException();
        }
    }
}
