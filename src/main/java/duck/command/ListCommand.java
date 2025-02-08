package duck.command;

import duck.task.Task;
import duck.ui.Storage;
import duck.ui.TaskList;

/**
 * Represents a command that prints all the tasks in the tasks list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage storage) {
        this.hasExecuted = true;
        StringBuilder sb = new StringBuilder();
        if (tasks.size() == 0) {
            sb.append("No tasks in the list.");
        } else {
            sb.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.getTask(i);
                sb.append((i + 1)).append(".").append(task).append("\n");
            }
        }
        this.executedResponse = sb.toString();
    }
}
