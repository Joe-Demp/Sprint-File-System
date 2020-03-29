package experiments;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

/**
 * Experiment to check the runtime class of Path objects
 */
public class PathToFileExperiment {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\demps\\Desktop\\COMP30770_2020_Project_2.pdf");
        Path path = file.toPath();

        System.out.println(file.getClass());
        System.out.println(path.getClass());
    }
}
