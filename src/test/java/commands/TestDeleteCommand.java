package commands;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDeleteCommand {
    public static final String testFilePath = "/j/home/myfiles/dogs.pdf";
    public static FileSystem mockFS;
    public static Path mockPath1;
    public static Path mockPath2;
    public static File mockFile1;

    @BeforeAll
    static void beforeAll() {
        // TODO reconsider this; can I really mock the FileSystem, Path and File objects?
        //      Maybe if I just want to get the responses.
        mockFS = mock(FileSystem.class);

        mockPath1 = mock(Path.class);
        mockPath2 = mock(Path.class);

        mockFile1 = mock(File.class);

        when(mockFS.getPath(testFilePath)).thenReturn(mockPath1);
        when(mockPath1.toFile()).thenReturn(mockFile1);
    }

    @Test
    public void test_OneArgConstructor() {
        DeleteCommand command = new DeleteCommand(mockPath1);

        assertEquals(mockPath1, command.getPath());
        assertFalse(command.isDirectoryAction());
        assertTrue(command.isFileAction());
    }

    @ParameterizedTest
    @ValueSource(strings = {"true", "false"})
    public void test_TwoArgConstructor(String canActOnDirectories) {
        boolean onDirectories = Boolean.parseBoolean(canActOnDirectories);
        DeleteCommand command = new DeleteCommand(mockPath1, onDirectories);

        assertEquals(mockPath1, command.getPath());
        assertEquals(onDirectories, command.isDirectoryAction());
        assertTrue(command.isFileAction());
    }

    @ParameterizedTest
    @CsvSource({"true,true", "true,false", "false,true", "false,false"})
    public void test_ThreeArgConstructor(String canActOnDirectories, String canActOnFiles) {
        boolean onDirectories = Boolean.parseBoolean(canActOnDirectories);
        boolean onFiles = Boolean.parseBoolean(canActOnFiles);
        DeleteCommand command = new DeleteCommand(mockPath1, onDirectories, onFiles);

        assertEquals(mockPath1, command.getPath());
        assertEquals(onDirectories, command.isDirectoryAction());
        assertEquals(onFiles, command.isFileAction());
    }

    @Test
    public void test_setPath() {
        DeleteCommand command = new DeleteCommand(mockPath1);
        assertEquals(mockPath1, command.getPath());

        command.setPath(mockPath2);
        assertEquals(mockPath2, command.getPath());
    }

    @Test
    public void test_setDirectoryAction() {
        DeleteCommand command = new DeleteCommand(mockPath1, true);
        assertTrue(command.isDirectoryAction());

        command.setDirectoryAction(false);
        assertFalse(command.isDirectoryAction());
    }

    @Test
    public void test_setFileAction() {
        DeleteCommand command = new DeleteCommand(mockPath1, true, true);
        assertTrue(command.isFileAction());

        command.setFileAction(false);
        assertFalse(command.isFileAction());
    }

    @Test
    public void test_execute_noPermissions() {
        DeleteCommand command = new DeleteCommand(mockPath1, false, false);
        CommandResponse response = command.execute();
        String expectedMessageSuffix = " FAILURE - File Not Deleted: Command had no permissions.";


        assertFalse(response.success());
        assertTrue(response.message().endsWith(expectedMessageSuffix));
    }

    // TODO tests for a positive action i.e. a correct deletion of a file or directory
}
