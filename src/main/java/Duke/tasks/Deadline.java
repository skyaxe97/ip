package Duke.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm");
    public LocalDateTime getBy() {
        return by;
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }
    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description);
        this.by = by;
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return ".[D]" + super.toString() + " (" + by.format(format) + ")";
    }
}
