package util;

import exceptions.UnsupportedSetException;

import java.io.File;

/**
 * A contract for any action in this solution that needs to access the file system.
 * <p>
 * This interface contains the basic supports that are required by clients using queries, commands or any other
 * facilities of this solution. This is to support the application of the CQRS design pattern in this solution.
 * </p>
 */
public interface FileSystemAction {
    /**
     * @return a response detailing the result of the execution
     */
    FileSystemActionResponse execute();

    /**
     * @return the file that this action targets
     */
    File getFile();

    /**
     * @param file the file that the action is to target
     */
    void setFile(File file);

    /**
     * @return true if this action is for directories, false otherwise
     */
    boolean isDirectoryAction();

    /**
     * @param canActOnDirectories true if you want this action to work on directories, false otherwise
     */
    void setDirectoryAction(boolean canActOnDirectories) throws UnsupportedSetException;

    /**
     * @return true if this action is for files, false otherwise
     */
    boolean isFileAction();

    /**
     * @param canActOnFiles true if you want this action to work on files, false otherwise
     */
    void setFileAction(boolean canActOnFiles) throws UnsupportedSetException;
}
