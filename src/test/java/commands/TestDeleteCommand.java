package commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.FileSystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class TestDeleteCommand {
    public static FileSystem mockFS;
    public static File mockFile1;
    public static File mockFile2;

    @BeforeAll
    static void beforeAll() {
        // TODO reconsider this; can I really mock the FileSystem, Path and File objects?
        //      Maybe if I just want to get the responses.
        mockFS = mock(FileSystem.class);

        mockFile1 = mock(File.class);
        mockFile2 = mock(File.class);
    }

    // todo fix the tests. It does not build

    @Test
    public void testConstructor() {
        DeleteCommand command = new DeleteCommand(mockFile1);

        assertEquals(mockFile1, command.getFile());
        assertFalse(command.isDirectoryAction());
        assertTrue(command.isFileAction());
    }

    @Test
    public void test_execute_noPermissions() {
        DeleteCommand command = new DeleteCommand(mockFile1, false, false);
        CommandResponse response = command.execute();
        String expectedMessageSuffix = " FAILURE - File Not Deleted: Command had no permissions.";


        assertFalse(response.success());
        assertTrue(response.message().endsWith(expectedMessageSuffix));
    }

    // TODO tests for a positive action i.e. a correct deletion of a file or directory
}
