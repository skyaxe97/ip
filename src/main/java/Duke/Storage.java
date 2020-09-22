package Duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Storage {
    protected String filePath;
    protected static File file;



    public Storage(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
    }

    /**
     * Creates the new
     */
    public static void initialise() {
        try {
            file.createNewFile();
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String input = s.nextLine();
                Parser.parseFileCommands(input);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

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
