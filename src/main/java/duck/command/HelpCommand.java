package duck.command;

import duck.ui.Storage;
import duck.ui.TaskList;
import duck.ui.Ui;

/**
 * Represents a command that prints all the commands in the application.
 */
public class HelpCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("""
                Usage:\s
                list - Show tasklist
                bye - Exit
                todo [description] - Create Todo Task
                deadline [description] /by [Date Time eg. yyyy-MM-dd HHmm] - Create Deadline Task
                event [description] /from [Start eg. yyyy-MM-dd HHmm] /to [End eg. yyyy-MM-dd HHmm] - Create Event Task
                mark [task_id] - Set Task as Done
                unmark [task_id] - Set Task as Not Done
                delete [task_id] - Delete Task
                find [keyword] - Find Tasks with keyword in its description""");
    }
}
