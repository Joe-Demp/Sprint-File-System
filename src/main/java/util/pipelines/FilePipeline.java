package util.pipelines;

import java.io.File;

/**
 * A contract for objects that pass {@code Files} from one entity to another.
 */
public interface FilePipeline {
    /**
     * Adds a {@code File} to the pipeline
     */
    void put(File file) throws InterruptedException;

    /**
     * Removes and returns a {@code File} from the pipeline
     *
     * @throws InterruptedException if the Thread backing this FilePipeline gets interrupted
     */
    File take() throws InterruptedException;

    /**
     * Signals to the receiver of this pipeline that the last element has been taken
     */
    default void close() throws InterruptedException {
        put(new NullFile());
    }
}
