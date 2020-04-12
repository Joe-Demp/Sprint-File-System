package queries;

import exceptions.UnsupportedSetException;

import java.io.File;

public class ClassFileQuery implements Query {
    public static final String extension = ".class";
    private File file;

    public ClassFileQuery(File file) {
        setFile(file);
    }

    @Override
    public QueryResponse execute() {
        ClassFileQueryResponse response;
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
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean isDirectoryAction() {
        return false;
    }

    @Override
    public void setDirectoryAction(boolean canActOnDirectories) throws UnsupportedSetException {
        if (canActOnDirectories) {
            throw new UnsupportedSetException(
                    String.format("Can't set %s to act on a directory", ClassFileQuery.class)
            );
        }
    }

    @Override
    public boolean isFileAction() {
        return true;
    }

    @Override
    public void setFileAction(boolean canActOnFiles) throws UnsupportedSetException {
        if (!canActOnFiles) {
            throw new UnsupportedSetException(
                    String.format("%s can only be set to act on files.", ClassFileQuery.class)
            );
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
