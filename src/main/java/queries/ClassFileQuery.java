package queries;

import exceptions.UnsupportedSetException;

import java.io.File;
import java.nio.file.Path;

public class ClassFileQuery implements Query {
    public static final String extension = ".class";
    private Path path;

    public ClassFileQuery(Path path) {
        setPath(path);
    }

    @Override
    public QueryResponse execute() {
        ClassFileQueryResponse response;
        File file = path.toFile();
        String msg;

        if (!file.exists()) {
            msg = String.format("%s does not exist", file.getName());
            response = new ClassFileQueryResponse(false, file, msg);
        } else if (file.getName().endsWith(extension)) {
            msg = String.format("%s found", file.getName());
            response = new ClassFileQueryResponse(true, file, msg);
        } else {
            msg = String.format("%s not a .class file", file.getName());
            response = new ClassFileQueryResponse(false, file, msg);
        }

        return response;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public boolean isDirectoryAction() {
        return false;
    }

    @Override
    public void setDirectoryAction(boolean canActOnDirectories) throws UnsupportedSetException {
        if (canActOnDirectories) {
            throw new UnsupportedSetException(String.format("Can't set %s to act on a directory", this.getClass()));
        }
    }

    @Override
    public boolean isFileAction() {
        return true;
    }

    @Override
    public void setFileAction(boolean canActOnFiles) throws UnsupportedSetException {
        if (!canActOnFiles) {
            throw new UnsupportedSetException(String.format("%s can only be set to act on files.", this.getClass()));
        }
    }

    public static class ClassFileQueryResponse extends QueryResponse {
        public ClassFileQueryResponse(boolean success, File file, String message) {
            super(success, file, message, indicator(success));
        }

        private static String indicator(boolean success) {
            return success ? ".class file found" : ".class file not found";
        }
    }
}
