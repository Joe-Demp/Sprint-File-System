package util.pipelines;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class LBQFilePipeline implements FilePipeline {
    private BlockingQueue<File> queue = new LinkedBlockingQueue<>();

    @Override
    public void put(File file) throws InterruptedException {
        queue.put(file);
    }

    @Override
    public File take() throws InterruptedException {
        return queue.take();
    }
}
