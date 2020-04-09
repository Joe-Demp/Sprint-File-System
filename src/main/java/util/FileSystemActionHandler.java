package util;

public interface FileSystemActionHandler {
    /**
     * @param action
     * @return
     */
    FileSystemActionResponse handle(FileSystemAction action);
}
