package commands;

import java.io.File;
import java.nio.file.Path;
import java.time.Instant;

public class DeleteCommand implements Command {
    private boolean directoryAction;
    private Path path;
    private boolean fileAction;

    /**
     * Constructs a DeleteCommand for a file with the specified path
     *
     * @param path the path of the file to delete
     */
    public DeleteCommand(Path path) {
        this(path, false, true);
    }

    /**
     * Constructs a DeleteCommand for a file or directory at the specified path
     *
     * @param path          the path of the file or directory to delete
     * @param onDirectories true if the command acts on directories, false if not
     */
    public DeleteCommand(Path path, boolean onDirectories) {
        this(path, onDirectories, true);
    }

    /**
     * Constructs a DeleteCommand for a file or directory at the specified path
     *
     * @param path          the path of the file or directory to delete
     * @param onDirectories true if the command acts on directories, false if not
     * @param onFiles       true if the command acts on files, false if not
     */
    public DeleteCommand(Path path, boolean onDirectories, boolean onFiles) {
        setPath(path);
        setDirectoryAction(onDirectories);
        setFileAction(onFiles);
    }

    @Override
    public CommandResponse execute() {
        // Checks that this command matches the given file type (file or directory)
        //  if all good, execute delete return a positive response
        File file = path.toFile();
        boolean result;

        if (file.isFile() && fileAction) {
            result = file.delete();
            return new DeleteCommandResponse(result, file,
                    result ?
                            String.format("File %s deleted", path) :
                            String.format("File %s not deleted", path));
        } else if (file.isDirectory() && directoryAction) {
            result = file.delete();
            return new DeleteCommandResponse(result, file,
                    result ?
                            String.format("Directory %s deleted", path) :
                            String.format("Directory %s not deleted", path));
        }

        // Failure if no permission to delete file or directory
        return new DeleteCommandResponse(false, file, "Command had no permissions.");
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public boolean isDirectoryAction() {
        return directoryAction;
    }

    @Override
    public void setDirectoryAction(boolean canActOnDirectories) {
        this.directoryAction = canActOnDirectories;
    }

    @Override
    public boolean isFileAction() {
        return fileAction;
    }

    @Override
    public void setFileAction(boolean canActOnFiles) {
        this.fileAction = canActOnFiles;
    }

    /**
     * A class to model responses from delete commands
     */
    public static class DeleteCommandResponse implements CommandResponse {
        private boolean success;
        private File file;
        private String message;

        public DeleteCommandResponse(boolean success, File file, String message) {
            this.success = success;

            String indicator = success ? "SUCCESS - File Deleted" : "FAILURE - File Not Deleted";
            this.message = String.format("%s -- %s - %s: %s", Instant.now(), file.toString(), indicator, message);
        }

        @Override
        public boolean success() {
            return success;
        }

        @Override
        public File file() {
            return file;
        }

        @Override
        public String message() {
            return message;
        }
    }
}
