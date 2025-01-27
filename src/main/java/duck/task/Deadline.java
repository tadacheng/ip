package duck.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a description and a deadline time.
 */
public class Deadline extends Task {
    protected LocalDateTime deadline;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyy, hh:mm a");

    /**
     * Initializes a Deadline task with the description and deadline time as a string.
     *
     * @param description The description of the deadline task.
     * @param by          The deadline time in the format "yyyy-MM-dd HHmm".
     */
    public Deadline(String description, String by) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        this.deadline = LocalDateTime.parse(by, formatter);
    }

    /**
     * Initializes a Deadline task with the description and deadline time as a LocalDateTime.
     *
     * @param description The description of the deadline task.
     * @param by          The deadline time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.deadline = by;
    }

    /**
     * Returns a string representation of the deadline task, including its type, status, description,
     * and the formatted deadline time.
     *
     * @return A string representing the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline.format(DATE_TIME_FORMATTER) + ")";
    }

    /**
     * Converts the deadline task to a format suitable for saving to a file.
     *
     * @return A string representation of the deadline task in file format.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | "
                + this.deadline;
    }
}
