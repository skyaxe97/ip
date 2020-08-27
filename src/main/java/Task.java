public class Task {
    protected String description;
    protected boolean isDone;

    public static int taskCount = 0;

    public Task (String description) {
        this.description = description;
        this.isDone = false;
        taskCount++;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void markAsDone () {
        this.isDone = true;
    }
}
