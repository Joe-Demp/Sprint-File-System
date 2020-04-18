package queries;

import java.io.File;

public abstract class AbstractQuery implements Query {
    protected File file;

    public AbstractQuery(File file) {
        this.file = file;
    }

    @Override
    public QueryResponse execute() {
        if (file.exists()) {
            return queryFile();
        }
        return FileNotFoundQueryResponse.get(file);
    }

    private QueryResponse queryFile() {
        if (poseQuery()) {
            return makeResponse(true);
        }
        return makeResponse(false);
    }

    /**
     * A predicate function that executes the 'question' of the query
     */
    protected abstract boolean poseQuery();

    /**
     * Creates a response, with a contextual message, for a successful or failed {@code Query}
     */
    protected abstract QueryResponse makeResponse(boolean success);

    public static class FileNotFoundQueryResponse extends QueryResponse {
        private FileNotFoundQueryResponse(boolean success, File file, String message) {
            super(success, file, message);
        }

        public static FileNotFoundQueryResponse get(File file) {
            return new FileNotFoundQueryResponse(false, file, "File not found");
        }
    }
}
