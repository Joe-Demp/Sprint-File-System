package util.pipelines;

import java.io.File;

/**
 * A File that never exists, to act as a marker for the end of a {@code FilePipeline}
 */
public class NullFile extends File {
    /**
     * A placeholder value as a path for this {@code File}
     */
    public static final String PATH = "Null File";

    /**
     * Default constructor
     */
    public NullFile() {
        super(PATH);
    }

    /**
     * @return the {@link #PATH} string
     */
    @Override
    public String getPath() {
        return PATH;
    }

    /**
     * Acts as a check to differentiate real {@code Files}, that exist, from {@code NullFiles}
     *
     * @return false, since {@code NullFiles} never exist
     */
    @Override
    public boolean exists() {
        return false;
    }
}
