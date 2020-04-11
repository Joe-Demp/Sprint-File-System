package queries;

import util.FileSystemActionResponse;
import util.QueryHandler;
import util.ResponseHandler;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A class to handle 'crawling' the file system to query files
 */
public class FileSystemCrawler implements Runnable {
    private Path searchRoot;
    private Class<? extends Query> queryClass;
    private Constructor<? extends Query> queryConstructor;
    private QueryHandler queryHandler;
    private ResponseHandler responseHandler;


    /**
     * Creates a file system crawler, starting from the specified path and querying files using the specified query
     *
     * @param searchRoot      the {@code Path} of the directory to start crawling the file system from
     * @param queryClass      the {@code Class} object that represents the {@code Query} to apply
     * @param queryHandler    the {@code Handler} that executes the queries, described by {@code queryClass}
     * @param responseHandler the {@code Handler} that routes QueryResponses to the correct endpoint
     * @throws NoSuchMethodException if the specified {@code queryClass} does not have a constructor that
     *                               accepts a single {@code Path} object
     */
    public FileSystemCrawler(Path searchRoot,
                             Class<? extends Query> queryClass,
                             QueryHandler queryHandler,
                             ResponseHandler responseHandler) throws NoSuchMethodException {
        setSearchRoot(searchRoot);
        this.queryClass = queryClass;
        this.queryHandler = queryHandler;
        this.responseHandler = responseHandler;
        this.queryConstructor = queryClass.getConstructor(Path.class);
    }

    @Override
    public void run() {
        // Do a breadth-first search of the file system tree
        Queue<Path> pathQueue = new LinkedList<>();
        pathQueue.add(searchRoot);

        while (!pathQueue.isEmpty()) {
            Path path = pathQueue.remove();
            File file = path.toFile();

            if (file.isDirectory()) {
                enqueueChildren(pathQueue, file);
            } else {
                queryFile(path);
            }
        }

        System.out.printf("FileSystemCrawler.run finished with query %s", queryClass);
    }

    /**
     * Adds files from a directory onto the Breadth First Queue.
     * If {@code directory} is not a directory on the file system, this method does nothing
     *
     * @param queue     the {@code Queue} of {@code Path} objects used to do Breadth First Search on the file system
     * @param directory the {@code File} that represents the directory with files to add to queue
     */
    private void enqueueChildren(Queue<Path> queue, File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File child : files) {
                queue.add(child.toPath());
            }
        }
    }

    /**
     * Constructs a {@code Query} of the type specified in this class's constructor for the file located at the given
     * {@code Path}
     *
     * @param path the location of the file to be queried
     */
    private void queryFile(Path path) {
        try {
            Query query = queryConstructor.newInstance(path);
            FileSystemActionResponse response = queryHandler.handle(query);
            responseHandler.handle(response);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
            // TODO add logging here
        }
    }

    /**
     * Sets the root of the Breadth First Tree for this {@code FileSystemCrawler}. Search begins from this point.
     *
     * @param searchRoot The location to begin a file system crawl from. Ideally this should be a directory. If a file
     *                   is specified, the {@code FileSystemCrawler} will begin search from the parent directory of said
     *                   file
     */
    private void setSearchRoot(Path searchRoot) {
        Path path = searchRoot;

        if (!searchRoot.toFile().isDirectory()) {
            path = searchRoot.getParent();
        }

        this.searchRoot = path;
    }
}
