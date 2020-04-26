package ie.Dempsey.SprintFS.queries;

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

    // Message Suffixes
    public static final String successMsg =
            String.format(" %s - SUCCESS .class file found", pathExistingClassFile);
    public static final String failFileExistsMsg =
            String.format(" %s - FAILURE .class file not found", pathExistingTextFile);
    public static final String failNoFileMsg =
            String.format(" %s - FAILURE file not found", pathNonExistingClassFile);

    public static File existingClassFile;
    public static File existingTextFile;
    public static File nonExistingClassFile;

    public static Query queryExistingClassFile;
    public static Query queryExistingTextFile;
    public static Query queryNonExistingClassFile;

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
    public void test_executeSuccess() {
        QueryResponse response = queryExistingClassFile.execute();

        assertNotNull(response);
        assertTrue(response.success());
        assertEquals(existingClassFile, response.file());

        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(successMsg, actualMessageSuffix);
    }

    @Test
    public void test_executeFailure_fileExists() {
        QueryResponse response = queryExistingTextFile.execute();

        assertNotNull(response);
        assertFalse(response.success());
        assertEquals(existingTextFile, response.file());

        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(failFileExistsMsg, actualMessageSuffix);
    }

    @Test
    public void test_executeFailure_noFile() {
        QueryResponse response = queryNonExistingClassFile.execute();

        assertNotNull(response);
        assertFalse(response.success());
        assertEquals(nonExistingClassFile, response.file());

        String actualMessageSuffix = response.message().split("--")[1];
        assertEquals(failNoFileMsg, actualMessageSuffix);
    }
}
