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
        File file = new NullFile();

        // todo fix this so the null file is not processed and output to the user
        do {
            try {
                file = pipeline.take();

                runCommand(file);
            } catch (InterruptedException e) {
                System.err.println("FilePipeline was interrupted while waiting");
                e.printStackTrace();
            }
        } while (!(file instanceof NullFile));

        System.out.printf("FileSystemEditor.run finished with command %s.\n", commandClass);
    }

    /**
     * TODO fill in
     *
     * @param file
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
