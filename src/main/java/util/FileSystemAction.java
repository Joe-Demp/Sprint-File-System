package util;

import exceptions.UnsupportedSetException;

import java.nio.file.Path;

/**
 * TODO 26/03/2020  Write the javadoc for FileSystemAction
 */
public interface FileSystemAction {
    /**
     * @return a response detailing the result of the execution
     */
    FileSystemActionResponse execute();

    /**
     * @return the path of the object that this action targets
     */
    Path getPath();

    /**
     * @param path the path to the object that the action is to target
     */
    void setPath(Path path);

    /**
     * @return true if this action is for directories, false otherwise
     */
    boolean isDirectoryAction();

    /**
     * @param canActOnDirectories
     */
    void setDirectoryAction(boolean canActOnDirectories) throws UnsupportedSetException;

    /**
     * @return true if this action is for files, false otherwise
     */
    boolean isFileAction();

    /**
     * @param canActOnFiles
     */
    void setFileAction(boolean canActOnFiles) throws UnsupportedSetException;
}
