package Duke.tasks;

public class Event extends Task {
    protected String time;

    public String getTime() {
        return this.time;
    }

    public Event(String description, String time) {
        super(description);
        this.time = time;
    }
    public Event(String description, String time, boolean isDone) {
        super(description);
        this.time = time;
        this.isDone = isDone;
    }
    @Override
    public String toString() {
        return ".[E]" + super.toString() + " (" + time + ")";
    }
}
