import commands.DeleteCommand;
import queries.ClassFileQuery;
import util.actors.FileSystemCrawler;
import util.actors.FileSystemEditor;
import util.handlers.CommandHandler;
import util.handlers.QueryHandler;
import util.pipelines.FilePipeline;
import util.pipelines.LBQFilePipeline;

import java.io.File;
import java.io.IOException;

public class Main {
    public static final String PATH = "C:\\Users\\demps\\Desktop\\SprintFS Demo\\";
    public static final int NUMBER_OF_FILES = 10;

    public static void main(String[] args) {
        File file = new File(PATH);
        try {
            initializeFileSystemDemo();
        } catch (IOException e) {
            System.err.println("Error in initializing the File System.");
            e.printStackTrace();
        }
        FilePipeline pipeline = new LBQFilePipeline();

        try {
            new Thread(new FileSystemCrawler(
                    file,
                    ClassFileQuery.class,
                    new QueryHandler(),
                    System.out::println,
                    pipeline)).start();

            new Thread(new FileSystemEditor(
                    pipeline,
                    DeleteCommand.class,
                    new CommandHandler(),
                    System.out::println)).start();
        } catch (NoSuchMethodException nsme) {
            System.out.println(nsme.getMessage());
        }
    }

    private static void initializeFileSystemDemo() throws IOException {
        for (int i = 0; i < NUMBER_OF_FILES; i++) {
            String filename = String.format("%sfile%d.class", PATH, i);
            new File(filename).createNewFile();
        }
    }
}
