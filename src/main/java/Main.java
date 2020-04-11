import queries.ClassFileQuery;
import queries.FileSystemCrawler;
import util.QueryHandler;

import java.io.File;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path path = new File("C:\\Users\\demps\\Desktop\\SprintFS Demo").toPath();

        try {
            new Thread(new FileSystemCrawler(
                    path,
                    ClassFileQuery.class,
                    new QueryHandler(),
                    System.out::println)).start();
        } catch (NoSuchMethodException nsme) {
            System.out.println(nsme.getMessage());
        }
    }
}
