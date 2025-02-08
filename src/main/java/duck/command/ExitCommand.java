package duck.command;

import duck.ui.Storage;
import duck.ui.TaskList;

/**
 * Represents a command that exits the application.
 * This command displays a farewell message to the user and ends the program.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Storage storage) {
        this.hasExecuted = true;
        this.executedResponse = "Bye. Hope to see you again soon!";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
