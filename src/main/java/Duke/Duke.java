package Duke;


public class Duke {
    public static final String COMMAND_BYE = "bye";

    private final Ui ui;
    private final TaskList taskList;
    private final Storage storage;
    public Duke (String filePath) {
        ui = new Ui();
        taskList = new TaskList();
        storage = new Storage(filePath);

    }


    public void run() {
        String input;
        ui.printWelcome();
        storage.initialise();
        Parser parser = new Parser();
        do {
            input = ui.readCommand();
            parser.parseUserCommand(input);
        } while (!input.equals(COMMAND_BYE));
        Ui.printGoodbye();


    }
    public static void main(String[] args){
        new Duke("data.txt").run();
    }
}
