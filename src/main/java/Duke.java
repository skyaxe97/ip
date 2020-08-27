import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        System.out.println("Hello! What is up sir?");
        Task[] taskList= new Task[100];
        String line;
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            line = in.nextLine();
            if (line.equals("bye")) {
                break;
            }

            else if (line.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < Task.taskCount; i++) {
                    System.out.println((i + 1) + ". [" + taskList[i].getStatusIcon() + "] " + taskList[i].description);
                }
            }

            else if (line.startsWith("done ")) {
                int idx = Integer.parseInt(line.substring(5)) - 1;
                taskList[idx].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("\t" + "[" + "\u2713" + "] " + taskList[idx].description);
            }

            else {
                taskList[Task.taskCount] = new Task (line);
                System.out.println("added: " + line);
            }
        }

        System.out.println("Goodbye sir");
    }
}
