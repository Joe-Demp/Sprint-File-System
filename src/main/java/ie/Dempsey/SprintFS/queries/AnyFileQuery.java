package ie.Dempsey.SprintFS.queries;

import java.io.File;

public class AnyFileQuery extends AbstractQuery {
    public AnyFileQuery(File file) {
        super(file);
    }

    @Override
    protected boolean poseQuery() {
        return true;
    }

    @Override
    protected QueryResponse makeResponse(boolean success) {
        String message = success ? "this is a file" : "this is not a file";
        return new QueryResponse(success, file, message);
    }
}
