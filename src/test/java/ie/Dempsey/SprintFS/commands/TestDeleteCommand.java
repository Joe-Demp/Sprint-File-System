package ie.Dempsey.SprintFS.commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestDeleteCommand {
    public static final String pathDeletes = "/j/myfiles/file1.class";
    public static final String pathDoesNotDelete = "/j/myfiles/file2.class";

    public static final String successMsg = String.format(" %s - SUCCESS deleted", pathDeletes);
    public static final String failMsg = String.format(" %s - FAILURE not deleted", pathDoesNotDelete);

    public static File fileDeletes;
    public static File fileDoesNotDelete;

    public static Command successfulCmd;
    public static Command failureCmd;

    @BeforeAll
    static void beforeAll() {
        fileDeletes = makeFileThatDeletes();
        fileDoesNotDelete = makeFileThatDoesNotDelete();

        successfulCmd = new DeleteCommand(fileDeletes);
        failureCmd = new DeleteCommand(fileDoesNotDelete);
    }

    private static File makeFileThatDeletes() {
        File file = mock(File.class);
        when(file.toString()).thenReturn(pathDeletes);
        when(file.delete()).thenReturn(true);
        return file;
    }

    private static File makeFileThatDoesNotDelete() {
        File file = mock(File.class);
        when(file.toString()).thenReturn(pathDoesNotDelete);
        when(file.delete()).thenReturn(false);
        return file;
    }

    @Test
    public void test_executeSuccess() {
        CommandResponse response = successfulCmd.execute();

        assertNotNull(response);
        assertTrue(response.success());
        assertEquals(fileDeletes, response.file());

        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(successMsg, actualMessageSuffix);
    }

    @Test
    public void test_executeFailure() {
        CommandResponse response = failureCmd.execute();

        assertNotNull(response);
        assertFalse(response.success());
        assertEquals(fileDoesNotDelete, response.file());

        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(failMsg, actualMessageSuffix);
    }

    /**
     * Checks that execute actually triggers 'delete' on the file
     */
    @Test
    public void test_executeTriggersDelete() {
        successfulCmd.execute();
        verify(fileDeletes).delete();
    }
}
