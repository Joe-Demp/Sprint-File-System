package util;

import java.io.File;

/**
 * TODO 26/03/2020  Write the javadoc for FileSystemActionResponse
 */
public interface FileSystemActionResponse {
    boolean success();

    File file();

    String message();
}
