package commands;

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
            Files.move(file.toPath(), target, StandardCopyOption.ATOMIC_MOVE);
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    @Override
    protected CommandResponse makeResponse(boolean success) {
        String message = success ? "file moved" : "file not moved";
        return new CommandResponse(success, file, message);
    }

    private void setTarget(String newDirectory) {
        String newFileName = makeCorrectFilename(newDirectory);
        File file = new File(newFileName);
        //file = takeFileOrParent(file);
        this.target = file.toPath();
    }

    private String makeCorrectFilename(String directory) {
        return formatDirectoryPathString(directory) + file.getName();
    }

    private String formatDirectoryPathString(String directory) {
        char lastCharacter = directory.charAt(directory.length() - 1);
        if (!isBackslash(lastCharacter)) {
            directory += '\\';
        }
        return directory;
    }

    private boolean isBackslash(char c) {
        return c == '\\';
    }

    private File takeFileOrParent(File file) {
        return file.isDirectory() ? file : file.getParentFile();
    }
}
