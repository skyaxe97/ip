import javax.swing.*;
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
            }   else if (line.equals("list")) {
<<<<<<< HEAD
                    printList(taskList);
            }   else if (line.startsWith("done ")) {
                    doTask(taskList, line);
            }   else if (line.startsWith("todo ")){
                    addToDo(taskList, line);

            }   else if (line.startsWith("deadline ")) {
                    addDeadline(taskList, line);

            }   else if (line.startsWith("event ")) {
                    addEvent(taskList,line);

            }   else {
                printErrorMessage();
            }
        }
        printGoodbye();
    }
    public static void doTask(Task[] taskList, String line) {
        int idx = Integer.parseInt(line.substring(5)) - 1;
        taskList[idx].markAsDone();
        printMarkedAsDoneMessage(taskList[idx].description);
        printRemainingTask(taskList);
    }
    public static void addToDo(Task[] taskList, String line) {
        String description = line.substring(5);
        taskList[Task.taskCount] = new ToDo(description);
        printTaskAddedMessage(description);
        printRemainingTask(taskList);
    }

    public static void addDeadline(Task[] taskList, String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(9, idx - 1);
            String by = line.substring(idx + 1);
            taskList[Task.taskCount] = new Deadline(description, by);
            printTaskAddedMessage(line);
            printRemainingTask(taskList);
        }   catch (StringIndexOutOfBoundsException e) {
            printErrorMessage();
        }
    }

    public static void addEvent(Task[] taskList, String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(6, idx - 1);
            String time = line.substring(idx + 1);
            taskList[Task.taskCount] = new Event(description, time);
            printTaskAddedMessage(line);
            printRemainingTask(taskList);
        }   catch (StringIndexOutOfBoundsException e) {
            printErrorMessage();
        }

    }
    public static void printList(Task[] taskList) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < Task.taskCount; i++) {
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

    public static void printTaskAddedMessage(String line) {
        System.out.println("added: " + line);
        System.out.println(separator);
    }

    public static void printWelcome() {
        System.out.println(separator);
        System.out.println("Welcome sir");
        System.out.println(separator);
    }

    public static void printGoodbye() {
=======
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < Task.taskCount; i++) {
                        System.out.println((i + 1) + ". [" + taskList[i].getStatusIcon() + "] " + taskList[i].description);
                    }
            }   else if (line.startsWith("done ")) {
                    int idx = Integer.parseInt(line.substring(5)) - 1;
                    taskList[idx].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("\t" + "[" + "\u2713" + "] " + taskList[idx].description);
            }   else {
                    taskList[Task.taskCount] = new Task (line);
                    System.out.println("added: " + line);
            }
        }
>>>>>>> 4a7194081ffd73f992f3152e22edc08029ca346b
        System.out.println("Goodbye sir");
        System.out.println(separator);
    }

    public static void printRemainingTask(Task[] taskList) {
        System.out.println("Now you have " + Task.taskCounter + " tasks in the list");
        System.out.println(separator);
    }
}
