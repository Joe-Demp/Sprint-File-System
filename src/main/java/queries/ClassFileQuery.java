package queries;

import java.io.File;

public class ClassFileQuery extends AbstractQuery {
    public static final String extension = ".class";

    public ClassFileQuery(File file) {
        super(file);
    }

    @Override
    protected boolean poseQuery() {
        return file.getName().endsWith(extension);
    }

    @Override
    protected QueryResponse makeResponse(boolean success) {
        String message = success ? ".class file found" : ".class file not found";
        return new QueryResponse(success, file, message);
    }
}
