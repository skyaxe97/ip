package Duke.tasks;

public class Task {
    public String description;
    public boolean isDone;
    public static int taskCounter = 0;

    public Task (String description) {
        this.description = description;
        this.isDone = false;
        taskCounter++;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbol
    }

    public void markAsDone () {
        if (!this.isDone) {
            this.isDone = true;
            taskCounter--;
        }
        else {
            System.out.println("Task already marked done.");
        }

    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}
