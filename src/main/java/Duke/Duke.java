package Duke;
import Duke.exceptions.DukeException;
import Duke.tasks.Deadline;
import Duke.tasks.Event;
import Duke.tasks.Task;
import Duke.tasks.ToDo;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

public class Duke {
    public static final String COMMAND_DONE = "done ";
    public static final String COMMAND_TODO = "todo ";
    public static final String COMMAND_DEADLINE = "deadline ";
    public static final String COMMAND_EVENT = "event ";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_BYE = "bye";
    public static final String COMMAND_HELP = "help";
    public static final String COMMAND_DELETE = "delete ";
    //Printed messages
    public static final String SEPARATOR = "================================";
    public static final String MESSAGE_TASK_IN_LIST = "Here are the tasks in your list:";
    public static final String MESSAGE_MARK_AS_DONE = "Nice! I've marked this task as done:";
    public static final String MESSAGE_WELCOME = "\tWelcome sir";
    public static final String MESSAGE_GOODBYE = "\tGoodbye sir";
    //Exception Messages
    public static final String EXCEPTION_INVALID_COMMAND = "Invalid command, please re-enter command";
    public static final String EXCEPTION_EMPTY_ARGUMENT = "Empty argument, please insert argument";
    public static final String MESSAGE_FILE_AND_DIR_CREATED = "New directory and file have been created.";
    
    
    public static void main(String[] args) {
        printWelcome();
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            readFromFile(taskList);
        } catch (IOException e) {
            System.err.println(e);
        }
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

    public static void runCommand(ArrayList<Task> taskList, String line) {
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
            } else if (line.startsWith(COMMAND_DELETE)) {
                deleteTask(taskList, line);
            } else {
                throw new DukeException(EXCEPTION_INVALID_COMMAND);
            }
            writeToFile(taskList);
        } catch (DukeException | IOException e) {
            System.err.println(e);
        }
    }

    public static void readFromFile (ArrayList<Task> taskList) throws IOException {
        File dir = new File("data");
        File f = new File("data/duke.txt");
        if (f.exists()) {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String input = s.nextLine();
                processCommand(taskList, input);
            }
        } else {
            if (dir.mkdir()) {
                System.out.println("Dir created");
                if (f.createNewFile()) {
                    System.out.println("File created");
                }
            }
        }
    }

    public static void processCommand(ArrayList<Task> taskList,String commandLine) {
        String[] input = commandLine.split(" . ", -1);
        switch (input[0]) {
        case "T":
            if (input[1].equals("true")) {
                taskList.add(new ToDo(input[2], true));
                Task.taskCounter--;
            } else {
                taskList.add(new ToDo(input[2]));
            }
            break;
        case "D":
            if (input[1].equals("true")) {
                taskList.add(new Deadline(input[2], input[3], true));
                Task.taskCounter--;
            } else {
                taskList.add(new Deadline(input[2], input[3]));
            }
            break;
        case "E":
            if (input[1].equals("true")) {
                taskList.add(new Event(input[2], input[3], true));
                Task.taskCounter--;
            } else {
                taskList.add(new Event(input[2], input[3]));
            }
            break;
        default:
            break;
        }
    }

    public static void doTask(ArrayList<Task> taskList, String line) {
        int idx = Integer.parseInt(line.substring(5)) - 1;
        taskList.get(idx).markAsDone();
        printMarkedAsDoneMessage(taskList.get(idx).description);
        printRemainingTask();
    }
    public static void deleteTask(ArrayList<Task> taskList, String line) {
        int idx = Integer.parseInt(line.substring(7)) - 1;
        //prevent double counting when decrementing counter
        if (!taskList.get(idx).isDone){
            Task.taskCounter--;
        }
        taskList.remove(idx);
        printList(taskList);
    }
    public static void addToDo(ArrayList<Task> taskList, String line) {
        try {
            String description = line.substring(5);
            if (description.isEmpty()) {
                throw new DukeException(EXCEPTION_EMPTY_ARGUMENT);
            }
            ToDo newToDo = new ToDo(description);
            taskList.add(newToDo);
            printTaskAddedMessage(taskList, description);
        } catch (StringIndexOutOfBoundsException | DukeException e) {
            System.err.println(e);
        }
    }

    public static void addDeadline(ArrayList<Task> taskList, String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(9, idx - 1);
            String by = line.substring(idx + 1);
            Deadline newDeadLine = new Deadline(description,by);
            taskList.add(newDeadLine);
            printTaskAddedMessage(taskList, line);
        }   catch (StringIndexOutOfBoundsException e) {
            System.err.println(e);
        }
    }

    public static void addEvent(ArrayList<Task> taskList, String line) {
        int idx = line.indexOf('/');
        try {
            String description = line.substring(6, idx - 1);
            String time = line.substring(idx + 1);
            Event newEvent = new Event(description, time);
            taskList.add(newEvent);
            printTaskAddedMessage(taskList, line);
        }   catch (StringIndexOutOfBoundsException e) {
            System.err.println(e);
        }
    }

    public static void printList(ArrayList<Task> taskList) {
        System.out.println(MESSAGE_TASK_IN_LIST);
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((Integer.toString(i + 1) + taskList.get(i).toString()));
        }
        System.out.println(SEPARATOR);
    }

    public static void writeToFile (ArrayList<Task> taskList) throws IOException {
        FileWriter fw = new FileWriter("data/duke.txt");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i) instanceof ToDo) {
                sb.append("T . " + taskList.get(i).isDone + " . " + taskList.get(i).description + '\n');
            } else if (taskList.get(i) instanceof  Deadline) {
                sb.append("D . " + taskList.get(i).isDone + " . " + taskList.get(i).description + " . " + ((Deadline) taskList.get(i)).getBy() + '\n');
            } else {
                sb.append("E . " + taskList.get(i).isDone + " . " + taskList.get(i).description + " . " + ((Event) taskList.get(i)).getTime() + '\n');
            }
        }
        fw.write(sb.toString());
        fw.close();
    }

    public static void printMarkedAsDoneMessage(String description) {
        System.out.println(MESSAGE_MARK_AS_DONE + "\n" + "\t" + "[" + "\u2713" + "] " + description + "\n" + SEPARATOR);
    }

    public static void printTaskAddedMessage(ArrayList<Task> taskList,String line) {
        System.out.println("added: " + line);
        printRemainingTask();
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

    public static void printRemainingTask() {
        System.out.println("Now you have " + Task.taskCounter + " tasks in the list" + "\n" + SEPARATOR);
    }
}
