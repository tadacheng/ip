package duck.task;

/**
 * Represents a to-do task with a description.
 */
public class Todo extends Task {
    /**
     * Initializes a Todo task with the specified description.
     *
     * @param description The description of the to-do task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the to-do task, including its type and description.
     *
     * @return A string representing the to-do task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts the to-do task to a format suitable for saving to a file.
     *
     * @return A string representation of the to-do task in file format.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }
}
