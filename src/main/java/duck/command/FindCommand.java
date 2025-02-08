package duck.command;

import java.util.List;

import duck.task.Task;
import duck.ui.Storage;
import duck.ui.TaskList;

/**
 * Represents a command that prints all the tasks found with keyword in the tasks list.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    @Override
    public void execute(TaskList tasks, Storage storage) {
        List<Task> foundTasks = tasks.findTasks(this.keyword);
        this.hasExecuted = true;
        StringBuilder sb = new StringBuilder();
        if (foundTasks.isEmpty()) {
            sb.append("No task found in the lists");
        } else {
            sb.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < foundTasks.size(); i++) {
                Task task = foundTasks.get(i);
                sb.append((i + 1)).append(".").append(task).append("\n");
            }
        }
        this.executedResponse = sb.toString();
    }
}
