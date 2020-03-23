package util;

import exceptions.BadPathException;

import javax.print.Doc;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class ClassFileDeleter {
    private final Path path;
    private static final DotClassFilenameFilter classFF = new DotClassFilenameFilter();
    private static final DirectoryFileFilter dirFF = new DirectoryFileFilter();

    /**
     *
     * @param path
     * @return
     * @throws BadPathException
     */
    public static ClassFileDeleter getInstance(Path path) throws BadPathException {
        File root = path.toFile();
        if (root.exists()) {
            if (root.isDirectory()) {
                return new ClassFileDeleter(path);
            } else {
                return new ClassFileDeleter(path.getParent());
            }
        }
        throw new BadPathException("The location specified by the path does not exist");
    }

    private ClassFileDeleter(Path pathToDirectory) {
        this.path = pathToDirectory;
    }

    /**
     *
     */
    public void execute() {
        Queue<File> directories = new LinkedList<>();
        File current;

        directories.add(path.toFile());
        while (!directories.isEmpty()) {
            current = directories.poll();

            for (File file : current.listFiles(classFF)) {
                file.deleteOnExit();
            }

            directories.addAll(Arrays.asList(current.listFiles(dirFF)));
        }
    }
}
