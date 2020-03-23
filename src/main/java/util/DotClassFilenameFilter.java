package util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A FilenameFilter that yields only files that end in .class
 */
public class DotClassFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        return name.matches(".class$");
    }
}
