package commands;

import util.CommandHandler;
import util.ResponseHandler;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FileSystemEditor implements Runnable {
    private Iterable<File> files;
    private Class<? extends Command> commandClass;
    private Constructor<? extends Command> commandConstructor;
    private CommandHandler commandHandler;
    private ResponseHandler responseHandler;

    public FileSystemEditor(Iterable<File> files,
                            Class<? extends Command> commandClass,
                            CommandHandler commandHandler,
                            ResponseHandler responseHandler) throws NoSuchMethodException {
        this.files = files;
        this.commandClass = commandClass;
        this.commandConstructor = commandClass.getConstructor(File.class);
        this.commandHandler = commandHandler;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        // TODO run this
        for (File file : files) {
            runCommand(file);
        }

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
