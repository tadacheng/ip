package duck.command;

import java.time.LocalDateTime;

import duck.exception.DuckException;
import duck.task.Recurring;
import duck.task.Task;
import duck.ui.Storage;
import duck.ui.TaskList;

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
     *
     * @param id The id of the task.
     * @param isMark Boolean of whether to mark Task.
     */
    public MarkCommand(int id, boolean isMark) {
        this.id = id;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DuckException {
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
            if (task instanceof Recurring recurring) {
                LocalDateTime nextOccurrence = recurring.getNextOccurrence();

                // Create a new recurring task with the updated next occurrence
                Recurring newRecurring = new Recurring(recurring.getDescription(),
                        nextOccurrence, recurring.getRecurrencePattern());

                // Add the new task to the task list
                tasks.addTask(newRecurring);
                storage.save(tasks.getAllTasks());
            }
        } else {
            task.markAsNotDone();
            sb.append("OK, I've marked this task as not done yet:");
        }
        sb.append("  ").append(task);
        this.executedResponse = sb.toString();
    }
}
