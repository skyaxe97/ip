package Duke;

import Duke.exceptions.DukeException;
import Duke.tasks.Deadline;
import Duke.tasks.Event;
import Duke.tasks.Task;
import Duke.tasks.ToDo;

public class Parser {
    public static final String EXCEPTION_INVALID_COMMAND = "Invalid command, please re-enter command";
    public static final String COMMAND_DONE = "done ";
    public static final String COMMAND_TODO = "todo ";
    public static final String COMMAND_DEADLINE = "deadline ";
    public static final String COMMAND_EVENT = "event ";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_HELP = "help";
    public static final String COMMAND_DELETE = "delete ";
    public static final String COMMAND_BYE = "bye";

    public Parser () {
    }

    /**
     * Parses commands from file that has '.' as the delimiter
     * @param command
     */
    public static void parseFileCommands (String command) {
        String[] input = command.split(" . ", -1);
        switch (input[0]) {
        case "T":
            if (input[1].equals("true")) {
                TaskList.addNewTask(new ToDo(input[2], true));
                Task.taskCounter--;
            } else {
                TaskList.addNewTask(new ToDo(input[2]));
            }
            break;
        case "D":
            if (input[1].equals("true")) {
                TaskList.addNewTask(new Deadline(input[2], input[3], true));
                Task.taskCounter--;
            } else {
                TaskList.addNewTask(new Deadline(input[2], input[3]));
            }
            break;
        case "E":
            if (input[1].equals("true")) {
                TaskList.addNewTask(new Event(input[2], input[3], true));
                Task.taskCounter--;
            } else {
                TaskList.addNewTask(new Event(input[2], input[3]));
            }
            break;
        default:
            break;
        }
    }

    /**
     * Parses user commands that is formatted according to the help
     * @param command
     */
    public static void parseUserCommand (String command) {
        try {
            if (command.equals(COMMAND_LIST)) {
                TaskList.printList();
            } else if (command.startsWith(COMMAND_DONE)) {
                TaskList.doTask(command);
            } else if (command.startsWith(COMMAND_TODO)) {
                TaskList.addToDo(command);
            } else if (command.startsWith(COMMAND_DEADLINE)) {
                TaskList.addDeadline(command);
            } else if (command.startsWith(COMMAND_EVENT)) {
                TaskList.addEvent(command);
            } else if (command.startsWith(COMMAND_HELP)) {
                Ui.printHelp();
            } else if (command.startsWith(COMMAND_DELETE)) {
                TaskList.deleteTask(command);
            } else if (command.startsWith(COMMAND_BYE)) {
            } else {
                throw new DukeException(EXCEPTION_INVALID_COMMAND);
            }
            TaskList.updateFile();
        } catch (DukeException e) {
            System.err.println(e);
        }
    }
}