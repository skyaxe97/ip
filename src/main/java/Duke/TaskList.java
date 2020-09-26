package Duke;

import Duke.exceptions.DukeException;
import Duke.tasks.Deadline;
import Duke.tasks.Event;
import Duke.tasks.Task;
import Duke.tasks.ToDo;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TaskList {
    public static final int TODO_CHAR_COUNT = 5;
    public static final int DEADLINE_CHAR_COUNT = 9;
    public static final int EVENT_CHAR_COUNT = 6;
    public static final int DELETE_CHAR_COUNT = 7;
    public static final int DONE_CHAR_COUNT = 5;
    public static final int FIND_CHAR_COUNT = 5;
    //Printed messages
    public static final String SEPARATOR = "================================";
    public static final String MESSAGE_TASK_IN_LIST = "Here are the tasks in your list:";
    //Exception Messages
    public static final String EXCEPTION_EMPTY_ARGUMENT = "Empty argument, please insert argument";
    protected static ArrayList<Task> taskList;
    public static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");


    public TaskList() {
        taskList = new ArrayList<>();
    }

    public static void printList() {
        Ui.printList(taskList);
    }

    public static void doTask(String line) {
        int idx = Integer.parseInt(line.substring(DONE_CHAR_COUNT)) - 1;
        taskList.get(idx).markAsDone();
        Ui.printMarkedAsDoneMessage(taskList.get(idx).description);
        Ui.printRemainingTask();
    }

    public static void addNewTask (Task task) {
        taskList.add(task);
    }

    public static void addToDo(String line) {
        try {
            String description = line.substring(TODO_CHAR_COUNT);
            if (description.isEmpty()) {
                throw new DukeException(EXCEPTION_EMPTY_ARGUMENT);
            }
            ToDo newToDo = new ToDo(description);
            taskList.add(newToDo);
            Ui.printTaskAddedMessage(taskList, description);
        } catch (StringIndexOutOfBoundsException | DukeException e) {
            System.err.println(e);
        }
    }

    public static void addDeadline(String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(DEADLINE_CHAR_COUNT, idx - 1);
            String by = line.substring(idx + 1);
            LocalDateTime dateTime = LocalDateTime.parse(by.trim(),inputFormat);
            Deadline newDeadLine = new Deadline(description,dateTime);
            taskList.add(newDeadLine);
            Ui.printTaskAddedMessage(taskList, line);
        }   catch (StringIndexOutOfBoundsException | DateTimeParseException e) {
            System.err.println(e);
        }
    }

    public static void addEvent(String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(EVENT_CHAR_COUNT, idx - 1);
            String time = line.substring(idx + 1);
            LocalDateTime dateTime = LocalDateTime.parse(time.trim(),inputFormat);
            Event newEvent = new Event(description, dateTime);
            taskList.add(newEvent);
            Ui.printTaskAddedMessage(taskList, line);
        }   catch (StringIndexOutOfBoundsException | DateTimeParseException e) {
            System.err.println(e);
        }
    }

    public static void deleteTask(String line) {
        int idx = Integer.parseInt(line.substring(DELETE_CHAR_COUNT)) - 1;
        //prevent double counting when decrementing counter
        if (!taskList.get(idx).isDone){
            Task.taskCounter--;
        }
        taskList.remove(idx);
        printList();
    }

    public static void updateFile () {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i) instanceof ToDo) {
                sb.append("T . " + taskList.get(i).isDone + " . " + taskList.get(i).description + '\n');
            } else if (taskList.get(i) instanceof Deadline) {
                sb.append("D . " + taskList.get(i).isDone + " . " + taskList.get(i).description + " . " + ((Deadline) taskList.get(i)).getBy() + '\n');
            } else {
                sb.append("E . " + taskList.get(i).isDone + " . " + taskList.get(i).description + " . " + ((Event) taskList.get(i)).getTime() + '\n');
            }
        }
        Storage.writeToFile(sb.toString());
    }

    public static void findTasks(String line) {
        Ui.printTaskQuery(taskList, line);
    }
}
