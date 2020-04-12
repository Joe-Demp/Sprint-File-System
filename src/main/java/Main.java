import queries.ClassFileQuery;
import queries.FileSystemCrawler;
import util.QueryHandler;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\demps\\Desktop\\SprintFS Demo");

        try {
            new Thread(new FileSystemCrawler(
                    file,
                    ClassFileQuery.class,
                    new QueryHandler(),
                    System.out::println)).start();
        } catch (NoSuchMethodException nsme) {
            System.out.println(nsme.getMessage());
        }
    }
}
