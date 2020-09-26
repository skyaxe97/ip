package Duke;

import Duke.exceptions.DukeException;
import Duke.tasks.Deadline;
import Duke.tasks.Event;
import Duke.tasks.Task;
import Duke.tasks.ToDo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TaskList {
    //Magic numbers for parsing
    public static final int TODO_CHAR_COUNT = 5;
    public static final int DEADLINE_CHAR_COUNT = 9;
    public static final int EVENT_CHAR_COUNT = 6;
    public static final int DELETE_CHAR_COUNT = 7;
    public static final int DONE_CHAR_COUNT = 5;
    //Printed messages
    public static final String EXCEPTION_EMPTY_ARGUMENT = "Empty argument, please insert argument";

    protected static ArrayList<Task> taskList;
    public static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");


    public TaskList() {
        taskList = new ArrayList<>();
    }

    public static void printList() {
        Ui.printList(taskList);
    }

    /**
     * Marks a task specified by user as done.
     * @param line This is the input command that contains
     *             index of task to be marked as done.
     */
    public static void doTask(String line) {
        int idx = Integer.parseInt(line.substring(DONE_CHAR_COUNT)) - 1;
        taskList.get(idx).markAsDone();
        Ui.printMarkedAsDoneMessage(taskList.get(idx).description);
        Ui.printRemainingTask();
    }

    /**
     * Adds new task to taskList
     * @param task Task to be added
     */
    public static void addNewTask (Task task) {
        taskList.add(task);
    }

    /**
     * Adds a todo task with description as per user input
     * @param line This is the input command that contains
     *             the description for the toDo task
     */
    public static void addToDo(String line) {
        try {
            String description = line.substring(TODO_CHAR_COUNT);
            if (description.isEmpty()) {
                throw new DukeException(EXCEPTION_EMPTY_ARGUMENT);
            }
            ToDo newToDo = new ToDo(description);
            taskList.add(newToDo);
            Ui.printTaskAddedMessage(description);
        } catch (StringIndexOutOfBoundsException | DukeException e) {
            System.err.println(e);
        }
    }

    /**
     * Adds a deadline task with description as per user input
     * @param line This is the input command that contains
     *             the description for the deadline task
     */
    public static void addDeadline(String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(DEADLINE_CHAR_COUNT, idx - 1);
            String by = line.substring(idx + 1);
            LocalDateTime dateTime = LocalDateTime.parse(by.trim(),inputFormat);
            Deadline newDeadLine = new Deadline(description,dateTime);
            taskList.add(newDeadLine);
            Ui.printTaskAddedMessage(line);
        }   catch (StringIndexOutOfBoundsException | DateTimeParseException e) {
            System.err.println(e);
        }
    }

    /**
     * Adds an event task with description as per user input.
     * @param line This is the input command that contains
     *             the description for the event task.
     */
    public static void addEvent(String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(EVENT_CHAR_COUNT, idx - 1);
            String time = line.substring(idx + 1);
            LocalDateTime dateTime = LocalDateTime.parse(time.trim(),inputFormat);
            Event newEvent = new Event(description, dateTime);
            taskList.add(newEvent);
            Ui.printTaskAddedMessage(line);
        }   catch (StringIndexOutOfBoundsException | DateTimeParseException e) {
            System.err.println(e);
        }
    }

    /**
     * Deletes task with index as per user input.
     * @param line This is the input command that contains
     *             the index of the task to be deleted.
     */
    public static void deleteTask(String line) {
        int idx = Integer.parseInt(line.substring(DELETE_CHAR_COUNT)) - 1;
        //prevent double counting when decrementing counter
        if (!taskList.get(idx).isDone){
            Task.taskCounter--;
        }
        taskList.remove(idx);
        printList();
    }

    /**
     * Updates the local data file with the new taskList.
     */
    public static void updateFile () {
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList) {
            if (task instanceof ToDo) {
                sb.append("T . " + task.isDone + " . " + task.description + '\n');
            } else if (task instanceof Deadline) {
                sb.append("D . " + task.isDone + " . " + task.description + " . " + ((Deadline) task).getBy() + '\n');
            } else {
                sb.append("E . " + task.isDone + " . " + task.description + " . " + ((Event) task).getTime() + '\n');
            }
        }
        Storage.writeToFile(sb.toString());
    }

    /**
     * Passes taskList to Ui to print tasks that
     * match user's query.
     * @param line This is the input command that contains
     *             the user query.
     */
    public static void findTasks(String line) {
        Ui.printTaskQuery(taskList, line);
    }
}
