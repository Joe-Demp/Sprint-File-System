package commands;

import queries.NullFile;
import util.CommandHandler;
import util.FilePipeline;
import util.ResponseHandler;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FileSystemEditor implements Runnable {
    private FilePipeline pipeline;
    private Class<? extends Command> commandClass;
    private Constructor<? extends Command> commandConstructor;
    private CommandHandler commandHandler;
    private ResponseHandler responseHandler;

    public FileSystemEditor(FilePipeline pipeline,
                            Class<? extends Command> commandClass,
                            CommandHandler commandHandler,
                            ResponseHandler responseHandler) throws NoSuchMethodException {
        this.pipeline = pipeline;
        this.commandClass = commandClass;
        this.commandConstructor = commandClass.getConstructor(File.class);
        this.commandHandler = commandHandler;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        File file = takeFile();

        while (!(file instanceof NullFile)) {
            runCommand(file);
            file = takeFile();
        }

        System.out.printf("FileSystemEditor.run finished with command %s.\n", commandClass);
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
        try {
            Command command = commandConstructor.newInstance(file);
            CommandResponse response = commandHandler.handle(command);
            responseHandler.handle(response);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
