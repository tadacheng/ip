package duck.command;

import duck.task.Task;
import duck.ui.Storage;
import duck.ui.TaskList;
import duck.ui.Ui;

import java.util.List;

/**
 * Represents a command that prints all the tasks found with keyword in the tasks list.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> foundTasks = tasks.findTasks(this.keyword);
        if (foundTasks.isEmpty()) {
            System.out.println("No task found in the lists");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < foundTasks.size(); i++) {
                Task task = foundTasks.get(i);
                System.out.println((i + 1) + "." + task);
            }
        }
    }
}
