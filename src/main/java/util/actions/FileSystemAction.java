package util.actions;

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
}
