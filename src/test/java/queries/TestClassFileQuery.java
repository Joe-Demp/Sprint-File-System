package queries;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestClassFileQuery {
    public static final String pathExistingClassFile = "/j/myfiles/file1.class";
    public static final String pathExistingTextFile = "/j/myfiles/file2.txt";
    public static final String pathNonExistingClassFile = "/j/myfiles/file3.class";

    public static File existingClassFile;   // a class file that exists
    public static File existingTextFile;   // a non-class file that exists
    public static File nonExistingClassFile;   // a class file that doesn't exist

    public static ClassFileQuery queryExistingClassFile;
    public static ClassFileQuery queryExistingTextFile;
    public static ClassFileQuery queryNonExistingClassFile;

    @BeforeAll
    static void setup() {
        existingClassFile = makeMockFile(pathExistingClassFile, "file1.class", true);
        existingTextFile = makeMockFile(pathExistingTextFile, "file2.txt", true);
        nonExistingClassFile = makeMockFile(pathNonExistingClassFile, "file3.class", false);
    }

    private static File makeMockFile(String path, String filename, boolean exists) {
        File mockFile = mock(File.class);
        when(mockFile.toString()).thenReturn(path);
        when(mockFile.getName()).thenReturn(filename);
        when(mockFile.exists()).thenReturn(exists);
        return mockFile;
    }

    @BeforeEach
    public void beforeEach() {
        queryExistingClassFile = new ClassFileQuery(existingClassFile);
        queryExistingTextFile = new ClassFileQuery(existingTextFile);
        queryNonExistingClassFile = new ClassFileQuery(nonExistingClassFile);
    }

    @AfterEach
    public void afterEach() {
        queryExistingClassFile = queryExistingTextFile = queryNonExistingClassFile = null;
    }

    @Test
    public void test_constructor() {
        assertEquals(existingClassFile, queryExistingClassFile.getFile());
        assertFalse(queryExistingClassFile.isDirectoryAction());
        assertTrue(queryExistingClassFile.isFileAction());
    }

    @Test
    public void test_executeSuccess() {
        QueryResponse response = queryExistingClassFile.execute();

        assertNotNull(response);
        assertTrue(response.success());
        assertEquals(existingClassFile, response.file());

        String expectedMessageSuffix = String.format(" %s - %s: %s", pathExistingClassFile,
                ".class file found", "file1.class found");
        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(expectedMessageSuffix, actualMessageSuffix);
    }

    @Test
    public void test_executeFailure_fileExists() {
        QueryResponse response = queryExistingTextFile.execute();

        assertNotNull(response);
        assertFalse(response.success());
        assertEquals(existingTextFile, response.file());

        String expectedMessageSuffix = String.format(" %s - %s: %s", pathExistingTextFile,
                ".class file not found", "file2.txt not a .class file");
        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(expectedMessageSuffix, actualMessageSuffix);
    }

    @Test
    public void test_executeFailure_noFile() {
        QueryResponse response = queryNonExistingClassFile.execute();

        assertNotNull(response);
        assertFalse(response.success());
        assertEquals(nonExistingClassFile, response.file());

        String expectedMessageSuffix = String.format(" %s - %s: %s", pathNonExistingClassFile,
                ".class file not found", "file3.class does not exist");
        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(expectedMessageSuffix, actualMessageSuffix);
    }
}
