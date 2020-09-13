package Duke;
import Duke.exceptions.DukeException;
import Duke.tasks.Deadline;
import Duke.tasks.Event;
import Duke.tasks.Task;
import Duke.tasks.ToDo;

import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static final String COMMAND_DONE = "done ";
    public static final String COMMAND_TODO = "todo ";
    public static final String COMMAND_DEADLINE = "deadline ";
    public static final String COMMAND_EVENT = "event ";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_BYE = "bye";
    public static final String COMMAND_HELP = "help";

    //Printed messages
    public static final String SEPARATOR = "================================";
    public static final String MESSAGE_TASK_IN_LIST = "Here are the tasks in your list:";
    public static final String MESSAGE_MARK_AS_DONE = "Nice! I've marked this task as done:";
    public static final String MESSAGE_WELCOME = "\tWelcome sir";
    public static final String MESSAGE_GOODBYE = "\tGoodbye sir";
    //Exception Messages
    public static final String EXCEPTION_INVALID_COMMAND = "Invalid command, please re-enter command";
    public static final String EXCEPTION_EMPTY_ARGUMENT = "Empty argument, please insert argument";

    public static void main(String[] args) {
        printWelcome();
        Task[] taskList = new Task[100];
        String line;
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            line = in.nextLine();
            if (line.equals(COMMAND_BYE)) {
                break;
            } else {
                runCommand(taskList, line);
            }
        }
        printGoodbye();
    }
    public static void runCommand(Task[] taskList, String line) {
        try {
            if (line.equals(COMMAND_LIST)) {
                printList(taskList);
            } else if (line.startsWith(COMMAND_DONE)) {
                doTask(taskList, line);
            } else if (line.startsWith(COMMAND_TODO)) {
                addToDo(taskList, line);
            } else if (line.startsWith(COMMAND_DEADLINE)) {
                addDeadline(taskList, line);
            } else if (line.startsWith(COMMAND_EVENT)) {
                addEvent(taskList, line);
            } else if (line.startsWith(COMMAND_HELP)) {
                printHelp();
            } else {
                throw new DukeException(EXCEPTION_INVALID_COMMAND);
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
                throw new DukeException(EXCEPTION_EMPTY_ARGUMENT);
            }
            taskList[Task.taskIdx] = new ToDo(description);
            printTaskAddedMessage(taskList, description);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println(e);
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
            System.err.println(e);
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
            System.err.println(e);
        }

    }
    public static void printList(Task[] taskList) {
        System.out.println(MESSAGE_TASK_IN_LIST);
        for (int i = 0; i < Task.taskIdx; i++) {
            System.out.println((Integer.toString(i + 1) + taskList[i]));
        }
        System.out.println(SEPARATOR);
    }

    public static void printMarkedAsDoneMessage(String description) {
        System.out.println(MESSAGE_MARK_AS_DONE + "\n" + "\t" + "[" + "\u2713" + "] " + description + "\n" + SEPARATOR);
    }

    public static void printTaskAddedMessage(Task[] taskList,String line) {
        System.out.println("added: " + line);
        printRemainingTask(taskList);
    }

    public static void printHelp() {
        System.out.println(SEPARATOR + "\nHere are the commands that you can use");
        System.out.println("todo {description}........................ to add new task");
        System.out.println("deadline {description} //by (date time}... to add new task with a dateline");
        System.out.println("event {description} //at (date time) ..... to add new event that takes place at a specific time");
        System.out.println("list...................................... list out all tasks");
        System.out.println("done [item num]........................... to mark a task as done");
        System.out.println("delete [item num]......................... to mark a task as done");
        System.out.println("bye....................................... to quit\n" + SEPARATOR);

    }
    public static void printWelcome() {
        System.out.println(SEPARATOR + "\n" + MESSAGE_WELCOME + "\n" + SEPARATOR);
    }

    public static void printGoodbye() {
        System.out.println(MESSAGE_GOODBYE + "\n" + SEPARATOR);
    }

    public static void printRemainingTask(Task[] taskList) {
        System.out.println("Now you have " + Task.taskCounter + " tasks in the list" + "\n" + SEPARATOR);
    }
}
