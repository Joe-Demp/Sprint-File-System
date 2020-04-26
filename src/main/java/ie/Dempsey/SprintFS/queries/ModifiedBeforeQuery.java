package ie.Dempsey.SprintFS.queries;

import java.io.File;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * todo fill in
 */
public class ModifiedBeforeQuery extends AbstractQuery {
    public static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);

    private final Instant deadline;

    public ModifiedBeforeQuery(File file, Instant deadline) {
        super(file);
        this.deadline = deadline;
    }

    @Override
    protected boolean poseQuery() {
        Instant fileModified = Instant.ofEpochMilli(file.lastModified());
        return fileModified.isBefore(deadline);
    }

    @Override
    protected QueryResponse makeResponse(boolean success) {
        String message = getFormattedMessage(success);
        return new QueryResponse(success, file, message);
    }

    private String getFormattedMessage(boolean success) {
        String successFormat = "created before %s";
        String failFormat = "created after %s";
        return success ?
                String.format(successFormat, dtFormatter.format(deadline)) :
                String.format(failFormat, dtFormatter.format(deadline));
    }
}
