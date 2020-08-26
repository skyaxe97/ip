import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        System.out.println("Hello! What is up sir?");

        String line;
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            line = in.nextLine();
            if (line.equals("bye")) {
                break;
            }
            else {
                System.out.println(line);
            }
        }

        System.out.println("Goodbye sir");
    }
}
