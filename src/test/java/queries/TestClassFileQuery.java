package queries;

import exceptions.UnsupportedSetException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestClassFileQuery {
    public static final String path1 = "/j/myfiles/file1.class";
    public static final String path2 = "/j/myfiles/file2.txt";
    public static final String path3 = "/j/myfiles/file3.class";

    public static Path mockPath1;
    public static Path mockPath2;
    public static Path mockPath3;

    public static File mockFile1;   // a class file that exists
    public static File mockFile2;   // a non-class file that exists
    public static File mockFile3;   // a class file that doesn't exist

    public static ClassFileQuery query1;
    public static ClassFileQuery query2;
    public static ClassFileQuery query3;

    @BeforeAll
    static void setup() {
        setupFiles();
        setupPaths();
    }

    private static void setupFiles() {
        mockFile1 = makeMockFile(path1, "file1.class", true);
        mockFile2 = makeMockFile(path2, "file2.txt", true);
        mockFile3 = makeMockFile(path3, "file3.class", false);
    }

    private static void setupPaths() {
        mockPath1 = makeMockPath(mockFile1);
        mockPath2 = makeMockPath(mockFile2);
        mockPath3 = makeMockPath(mockFile3);
    }

    private static File makeMockFile(String path, String filename, boolean exists) {
        File mockFile = mock(File.class);
        when(mockFile.toString()).thenReturn(path);
        when(mockFile.getName()).thenReturn(filename);
        when(mockFile.exists()).thenReturn(exists);
        return mockFile;
    }

    private static Path makeMockPath(File mockFile) {
        Path mockPath = mock(Path.class);
        when(mockPath.toFile()).thenReturn(mockFile);
        return mockPath;
    }

    @BeforeEach
    public void beforeEach() {
        query1 = new ClassFileQuery(mockPath1);
        query2 = new ClassFileQuery(mockPath2);
        query3 = new ClassFileQuery(mockPath3);
    }

    @AfterEach
    public void afterEach() {
        query1 = query2 = query3 = null;
    }

    @Test
    public void test_constructor() {
        assertEquals(mockPath1, query1.getPath());
        assertFalse(query1.isDirectoryAction());
        assertTrue(query1.isFileAction());
    }

    @Test
    public void test_executeSuccess() {
        QueryResponse response = query1.execute();

        assertNotNull(response);
        assertTrue(response.success());
        assertEquals(mockFile1, response.file());

        String expectedMessageSuffix = String.format(" %s - %s: %s", path1, ".class file found", "file1.class found");
        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(expectedMessageSuffix, actualMessageSuffix);
    }

    @Test
    public void test_executeFailure_fileExists() {
        QueryResponse response = query2.execute();

        assertNotNull(response);
        assertFalse(response.success());
        assertEquals(mockFile2, response.file());

        String expectedMessageSuffix = String.format(" %s - %s: %s", path2, ".class file not found", "file2.txt not a .class file");
        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(expectedMessageSuffix, actualMessageSuffix);
    }

    @Test
    public void test_executeFailure_noFile() {
        QueryResponse response = query3.execute();

        assertNotNull(response);
        assertFalse(response.success());
        assertEquals(mockFile3, response.file());

        String expectedMessageSuffix = String.format(" %s - %s: %s", path3, ".class file not found", "file3.class does not exist");
        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(expectedMessageSuffix, actualMessageSuffix);
    }

    @Test
    public void test_get_set_Path() {
        assertEquals(mockPath1, query1.getPath());

        query1.setPath(mockPath2);
        assertEquals(mockPath2, query1.getPath());
    }

    @Test
    public void test_isDirectoryAction() {
        assertFalse(query1.isDirectoryAction());
    }

    @Test
    public void test_setDirectoryAction() {
        assertDoesNotThrow(() -> query1.setDirectoryAction(false));

        Throwable throwable = assertThrows(
                UnsupportedSetException.class,
                () -> query1.setDirectoryAction(true));

        assertNotNull(throwable.getMessage(), "Error messages should not be null.");
    }

    @Test
    public void test_isFileAction() {
        assertTrue(query1.isFileAction());
    }

    @Test
    public void test_setFileAction() {
        assertDoesNotThrow(() -> query1.setFileAction(true));

        Throwable throwable = assertThrows(
                UnsupportedSetException.class,
                () -> query1.setFileAction(false));

        assertNotNull(throwable.getMessage(), "Error messages should not be null.");
    }
}
