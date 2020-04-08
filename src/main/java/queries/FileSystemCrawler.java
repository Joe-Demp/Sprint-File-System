package queries;

import util.QueryHandler;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A class to handle 'crawling' the file system to query files
 */
public class FileSystemCrawler implements Runnable {
    private Path searchRoot;
    private Class<? extends Query> queryClass;
    private QueryHandler handler;

    /**
     * Creates a file system crawler, starting from the specified path and querying files using the specified query
     *
     * @param searchRoot the directory
     * @param queryClass
     * @param handler
     */
    public FileSystemCrawler(Path searchRoot, Class<? extends Query> queryClass, QueryHandler handler) {
        setSearchRoot(searchRoot);
        this.queryClass = queryClass;
        this.handler = handler;
    }

    @Override
    public void run() {
        // Do a breadth-first search of the file system tree
        Queue<Path> pathQueue = new LinkedList<>();
        pathQueue.add(searchRoot);

        while (!pathQueue.isEmpty()) {
            File file = pathQueue.remove().toFile();

            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    pathQueue.add(child.toPath());
                }
            } else {
                // create a query and pass it to the handler
            }
        }
    }

    /**
     * @param searchRoot
     */
    private void setSearchRoot(Path searchRoot) {
        Path path = searchRoot;

        if (!searchRoot.toFile().isDirectory()) {
            path = searchRoot.getParent();
        }

        this.searchRoot = path;
    }
}
