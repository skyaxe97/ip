package Duke;

import Duke.exceptions.DukeException;
import Duke.tasks.Task;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all actions that interacts with the user interface.
 * This includes reading in inputs and printing out outputs.
 */
public class Ui {
    public static final int FIND_CHAR_COUNT = 5;
    public static final String SEPARATOR = "================================";
    public static final String MESSAGE_WELCOME = "\tWelcome sir";
    public static final String MESSAGE_GOODBYE = "\tGoodbye sir";
    public static final String MESSAGE_MARK_AS_DONE = "Nice! I've marked this task as done:";
    public static final String MESSAGE_TASK_IN_LIST = "Here are the tasks in your list:";
    public static final String MESSAGE_TASK_QUERY = "Here are the matching tasks in your list:";
    public static final String EXCEPTION_EMPTY_ARGUMENT = "Empty argument, please insert argument";


    public Ui(){
    }

    public static void printHelp() {
        System.out.println(SEPARATOR + "\nHere are the commands that you can use");
        System.out.println("todo {description}.............................. to add new task");
        System.out.println("deadline {description} / {yyyy-mm-dd,HH:mm}... to add new task with a dateline");
        System.out.println("event {description} / {yyyy-mm-dd,HH:mm} ..... to add new event that takes place at a specific time");
        System.out.println("list............................................ list out all tasks");
        System.out.println("done [item num]................................. to mark a task as done");
        System.out.println("delete [item num]............................... to mark a task as done");
        System.out.println("bye............................................. to quit\n" + SEPARATOR);
    }

    /**
     * Reads in user command
     * @return User command as String
     */
    public static String readCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();

    }
    public static void printWelcome() {
        System.out.println(SEPARATOR + "\n" + MESSAGE_WELCOME + "\n" + SEPARATOR);
    }

    public static void printGoodbye() {
        System.out.println(MESSAGE_GOODBYE + "\n" + SEPARATOR);
    }

    public static void printMarkedAsDoneMessage(String description) {
        System.out.println(MESSAGE_MARK_AS_DONE + "\n" + "\t" + "[" + "\u2713" + "] " + description + "\n" + SEPARATOR);
    }

    public static void printTaskAddedMessage(String line) {
        System.out.println("added: " + line);
        printRemainingTask();
    }
    public static void printRemainingTask() {
        System.out.println("Now you have " + Task.taskCounter + " tasks in the list" + "\n" + SEPARATOR);
    }

    /**
     * Prints entire taskList with the full descriptions.
     * @param taskList This is the taskList to be traversed
     *                 and printed.
     */
    public static void printList(ArrayList<Task> taskList) {
        System.out.println(MESSAGE_TASK_IN_LIST);
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((Integer.toString(i + 1) + taskList.get(i).toString()));
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Takes in taskList from TaskList and prints all
     * tasks that matches user query.
     * @param taskList This is the taskList to be traversed
     *                 to find all matches.
     * @param line This is the input command that contains
     *             the user's query.
     */
    public static void printTaskQuery(ArrayList<Task> taskList, String line) {
        System.out.println(SEPARATOR + "\n" + MESSAGE_TASK_QUERY);
        try {
            String query = line.substring(FIND_CHAR_COUNT);
            if (query.isEmpty()) {
                throw new DukeException(EXCEPTION_EMPTY_ARGUMENT);
            }
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.get(i).description.contains(query)) {
                    System.out.println((Integer.toString(i + 1) + taskList.get(i).toString()));
                }
            }
        } catch (DukeException e) {
            System.err.println(e);
        }
        System.out.println(SEPARATOR);
    }
}
