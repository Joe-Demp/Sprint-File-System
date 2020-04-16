package queries;

import util.FilePipeline;
import util.QueryHandler;
import util.ResponseHandler;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * A class to handle 'crawling' the file system to query files
 */
public class FileSystemCrawler implements Runnable {
    private FilePipeline pipeline;
    private File searchRoot;
    private Constructor<? extends Query> queryConstructor;
    private QueryHandler queryHandler;
    private ResponseHandler responseHandler;

    /**
     * Constructs a {@code FileSystemCrawler} without a FilePipeline. This can be used to just run queries without
     * passing {@code Files} to a {@code FileSystemEditor}.
     *
     * @param searchRoot      the {@code File} representing the directory to start crawling the file system from
     * @param queryClass      the {@code Class} object that represents the {@code Query} to apply
     * @param queryHandler    the {@code Handler} that executes the queries, described by {@code queryClass}
     * @param responseHandler the {@code Handler} that routes QueryResponses to the correct endpoint
     * @throws NoSuchMethodException if the specified {@code queryClass} does not have a constructor that
     *                               accepts a single {@code Path} object
     */
    public FileSystemCrawler(File searchRoot,
                             Class<? extends Query> queryClass,
                             QueryHandler queryHandler,
                             ResponseHandler responseHandler) throws NoSuchMethodException {
        this(searchRoot, queryClass, queryHandler, responseHandler, null);
    }

    /**
     * Creates a file system crawler, starting from the specified path and querying files using the specified query
     *
     * @param searchRoot      the {@code File} representing the directory to start crawling the file system from
     * @param queryClass      the {@code Class} object that represents the {@code Query} to apply
     * @param queryHandler    the {@code Handler} that executes the queries, described by {@code queryClass}
     * @param responseHandler the {@code Handler} that routes QueryResponses to the correct endpoint
     * @param pipeline        a collection to hold {@code Files} in, to pass to other programs, potentially a
     *                        {@link commands.FileSystemEditor}
     * @throws NoSuchMethodException if the specified {@code queryClass} does not have a constructor that
     *                               accepts a single {@code Path} object
     */
    public FileSystemCrawler(File searchRoot,
                             Class<? extends Query> queryClass,
                             QueryHandler queryHandler,
                             ResponseHandler responseHandler,
                             FilePipeline pipeline) throws NoSuchMethodException {
        setSearchRoot(searchRoot);
        this.queryHandler = queryHandler;
        this.responseHandler = responseHandler;
        this.pipeline = pipeline;
        this.queryConstructor = queryClass.getConstructor(File.class);
    }

    @Override
    public void run() {
        // Do a breadth-first search of the file system tree
        Queue<File> pathQueue = new LinkedList<>();
        pathQueue.add(searchRoot);

        while (!pathQueue.isEmpty()) {
            File file = pathQueue.remove();

            if (file.isDirectory()) {
                enqueueChildren(pathQueue, file);
            } else {
                QueryResponse response = queryFile(file);
                respond(response);
            }
        }
        closePipeline();
    }

    /**
     * Adds files from a directory onto the Breadth First Queue.
     * If {@code directory} is not a directory on the file system, this method does nothing
     *
     * @param queue     the {@code Queue} of {@code File} objects used to do Breadth First Search on the file system
     * @param directory the {@code File} that represents the directory with files to add to queue
     */
    private void enqueueChildren(Queue<File> queue, File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            Collections.addAll(queue, files);
        }
    }

    /**
     * Creates a {@code Query} of the type specified in this class's constructor for the file represented by the
     * given {@code File}, passes it to the {@code QueryHandler} and returns a response.
     *
     * @param file the file to be queried
     * @return the response for the given query
     */
    private QueryResponse queryFile(File file) {
        try {
            Query query = queryConstructor.newInstance(file);
            return queryHandler.handle(query);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.printf("Issue with queryConstructor.newInstance(file) in %s.\n", FileSystemCrawler.class);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deals with {@code QueryResponses}
     *
     * @param response the {@code QueryResponse} to process
     */
    private void respond(QueryResponse response) {
        responseHandler.handle(response);

        // if this FileCrawler is associated with a pipeline and the Query was a success
        if (Objects.nonNull(pipeline) && response.success()) {
            try {
                pipeline.put(response.file());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the root of the Breadth First Tree for this {@code FileSystemCrawler}. Search begins from this point.
     *
     * @param searchRoot The {@code File} representing the start point of the crawl. Ideally this should be a directory.
     *                   If a file is specified, the {@code FileSystemCrawler} will begin search from
     *                   the parent directory of said file
     */
    private void setSearchRoot(File searchRoot) {
        File file = searchRoot;

        if (!searchRoot.isDirectory()) {
            file = searchRoot.getParentFile();
        }

        this.searchRoot = file;
    }

    private void closePipeline() {
        try {
            pipeline.close();
        } catch (InterruptedException e) {
            System.err.println("Error on closing the FilePipeline.");
            e.printStackTrace();
        }
    }
}
