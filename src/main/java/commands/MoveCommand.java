package commands;

import exceptions.NonExistentFileException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Moves a file from its location to another stated
 */
public class MoveCommand extends AbstractCommand {
    private Path target;

    public MoveCommand(File file, String newDirectory) {
        super(file);
        setTarget(newDirectory);
    }

    @Override
    protected boolean triggerAction() {
        try {
            Files.move(file.toPath(), target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.COPY_ATTRIBUTES);
            return true;
        } catch (IOException ignored) {
        }
        return false;
    }

    @Override
    protected CommandResponse makeResponse(boolean success) {
        throw new NotImplementedException();
    }

    private void setTarget(String newDirectory) {
        File file = makeFileFromPath(newDirectory);
        file = makeDirectory(file);
        this.target = file.toPath();
    }

    /**
     * Throws a RuntimeException if the File does not exist
     */
    private File makeFileFromPath(String path) {
        File file = new File(path);

        if (file.exists()) {
            return file;
        } else {
            throw new NonExistentFileException(String.format("File %s does not exist", file));
        }
    }

    /**
     * Checks if a file is a directory. If so the file is returned. If not, its parent is returned
     */
    private File makeDirectory(File file) {
        return file.isDirectory() ? file : file.getParentFile();
    }
}
