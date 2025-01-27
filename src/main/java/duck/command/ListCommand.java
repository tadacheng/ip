package duck.command;

import duck.ui.Storage;
import duck.task.Task;
import duck.ui.TaskList;
import duck.ui.Ui;

/**
 * Represents a command that prints all the tasks in the tasks list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() == 0) {
            System.out.println("No tasks in the list.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.getTask(i);
                System.out.println((i + 1) + "." + task);
            }
        }
    }
}
