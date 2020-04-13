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
    void put(File file);

    /**
     * todo fill in
     *
     * @return
     */
    File take();

    default void close() {
        put(new NullFile());
    }
}
