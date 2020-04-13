package util;

import queries.NullFile;

import java.io.File;

/**
 * todo fill in
 */
public interface FilePipeline {
    /**
     * todo fill in
     *
     * @param file
     */
    void put(File file) throws InterruptedException;

    /**
     * todo fill in
     *
     * @return
     */
    File take() throws InterruptedException;

    default void close() throws InterruptedException {
        put(new NullFile());
    }
}
