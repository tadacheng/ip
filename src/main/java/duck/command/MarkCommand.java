package duck.command;

import duck.exception.DuckException;
import duck.task.Task;
import duck.ui.Storage;
import duck.ui.TaskList;
import duck.ui.Ui;

/**
 * Represents a command that marks a task as either done or not done.
 * This command updates the status of a specific task in the task list
 * and saves the updated task list to storage.
 */
public class MarkCommand extends Command {
    private final int id;
    private final boolean isMark;

    /**
     * The command to mark a task as done or not done.
     * @param id The id of the task.
     * @param isMark Boolean of whether to mark Task.
     */
    public MarkCommand(int id, boolean isMark) {
        this.id = id;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        if (this.id < 0 || this.id >= tasks.size()) {
            throw new DuckException("Invalid task number. Use list to view task id");
        }
        Task task = tasks.getTask(id);
        storage.save(tasks.getAllTasks());
        this.hasExecuted = true;
        StringBuilder sb = new StringBuilder();
        if (isMark) {
            task.markAsDone();
            sb.append("Nice! I've marked this task as done:");
        } else {
            task.markAsNotDone();
            sb.append("OK, I've marked this task as not done yet:");
        }
        sb.append("  ").append(task);
        this.executedResponse = sb.toString();
    }
}
