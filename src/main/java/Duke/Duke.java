package Duke;
import Duke.exceptions.DukeException;
import Duke.tasks.Deadline;
import Duke.tasks.Event;
import Duke.tasks.Task;
import Duke.tasks.ToDo;

import java.util.Scanner;

public class Duke {
    public static final String separator = "================================";

    public static void main(String[] args) {
        printWelcome();
        Task[] taskList= new Task[100];
        String line;
        Scanner in = new Scanner(System.in);

        
        while (in.hasNextLine()) {
            line = in.nextLine();
            if (line.equals("bye")) {
                break;
            } else {
                runCommand(taskList, line);
            }
        }
        printGoodbye();
    }
    public static void runCommand(Task[] taskList, String line) {
        try {
            if (line.equals("list")) {
                printList(taskList);
            } else if (line.startsWith("done ")) {
                doTask(taskList, line);
            } else if (line.startsWith("todo ")) {
                addToDo(taskList, line);
            } else if (line.startsWith("deadline ")) {
                addDeadline(taskList, line);
            } else if (line.startsWith("event ")) {
                addEvent(taskList, line);
            } else {
                throw new DukeException("Unrecognised input");
            }
        } catch (DukeException e) {
            System.err.println(e);
        }
    }
    public static void doTask(Task[] taskList, String line) {
        int idx = Integer.parseInt(line.substring(5)) - 1;
        taskList[idx].markAsDone();
        printMarkedAsDoneMessage(taskList[idx].description);
        printRemainingTask(taskList);
    }
    public static void addToDo(Task[] taskList, String line) {
        try {
            String description = line.substring(5);
            if (description.isEmpty()) {
                throw new DukeException("Invalid input");
            }
            taskList[Task.taskIdx] = new ToDo(description);
            printTaskAddedMessage(taskList, description);
        } catch (StringIndexOutOfBoundsException e) {
            printErrorMessage();
        } catch (DukeException e) {
            System.err.println(e);
        }
    }

    public static void addDeadline(Task[] taskList, String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(9, idx - 1);
            String by = line.substring(idx + 1);
            taskList[Task.taskIdx] = new Deadline(description, by);
            printTaskAddedMessage(taskList, line);
        }   catch (StringIndexOutOfBoundsException e) {
            printErrorMessage();
        }
    }

    public static void addEvent(Task[] taskList, String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(6, idx - 1);
            String time = line.substring(idx + 1);
            taskList[Task.taskIdx] = new Event(description, time);
            printTaskAddedMessage(taskList, line);
        }   catch (StringIndexOutOfBoundsException e) {
            printErrorMessage();
        }

    }
    public static void printList(Task[] taskList) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < Task.taskIdx; i++) {
            System.out.println((Integer.toString(i + 1) + taskList[i]));
        }
        System.out.println(separator);
    }

    public static void printErrorMessage () {
        System.out.println(separator);
        System.out.println("Input error!!");
        System.out.println(separator);
    }
    public static void printMarkedAsDoneMessage(String description) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("\t" + "[" + "\u2713" + "] " + description);
        System.out.println(separator);
    }

    public static void printTaskAddedMessage(Task[] taskList,String line) {
        System.out.println("added: " + line);
        printRemainingTask(taskList);
    }

    public static void printWelcome() {
        System.out.println(separator);
        System.out.println("Welcome sir");
        System.out.println(separator);
    }

    public static void printGoodbye() {
        System.out.println("Goodbye sir");
        System.out.println(separator);
    }

    public static void printRemainingTask(Task[] taskList) {
        System.out.println("Now you have " + Task.taskCounter + " tasks in the list");
        System.out.println(separator);
    }
}
