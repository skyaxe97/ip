package Duke;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles all actions that involved the local data file.
 */
public class Storage {
    protected String filePath;
    protected static File file;



    public Storage(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
    }

    /**
     * Creates the new file and directory.
     */
    public static void initialise() {
        try {
            file.createNewFile();
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String input = s.nextLine();
                Parser.parseFileCommands(input);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Write the input task to the local file
     * @param task Task, as a string, to be written to file.
     */
    public static void writeToFile(String task) {
        try {
            FileWriter fw = new FileWriter("data.txt");
            fw.write(task.toString());
            fw.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
