package duck.task;

/**
 * Represents a task with a description and a status indicating whether it is completed.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initializes a Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task, represented as an "X" if done, or a space if not done.
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Marks the task as done by setting its status to true.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done by setting its status to false.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task, including its status icon and description.
     *
     * @return A string representing the task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() +"] " + this.description;
    }

    /**
     * Converts the task to a format suitable for saving to a file.
     * This method must be implemented by subclasses to define how each type of task should be saved.
     *
     * @return A string representation of the task in file format.
     */
    public abstract String toFileFormat();
}
