import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        System.out.println("Hello! What is up sir?");
        String[] taskList = new String[100];
        int taskCount = 0;
        String line;
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            line = in.nextLine();
            if (line.equals("bye")) {
                break;
            }
            else if (line.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + taskList[i]);
                }
            }

            else {
                taskList[taskCount] = line;
                System.out.println("added: " + line);
                taskCount++;
            }
        }

        System.out.println("Goodbye sir");
    }
}
