package duck.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a description, start time, and end time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyy, hh:mm a");

    /**
     * Initializes an Event task with the specified description and time range as strings.
     *
     * @param description The description of the event task.
     * @param from        The start time of the event in the format "yyyy-MM-dd HHmm".
     * @param to          The end time of the event in the format "yyyy-MM-dd HHmm".
     */
    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        this.from = LocalDateTime.parse(from, formatter);
        this.to = LocalDateTime.parse(to, formatter);
    }

    /**
     * Initializes an {@code Event} task with the specified description and time range as LocalDateTime.
     *
     * @param description The description of the event task.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }


    /**
     * Returns a string representation of the task, including its status icon and description.
     *
     * @return A string representing the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(DATE_TIME_FORMATTER)
                + " to: " + this.to.format(DATE_TIME_FORMATTER) + ")";
    }

    /**
     * Converts the event task to a format suitable for saving to a file.
     *
     * @return A string representation of the event task in file format.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.from + " | " + this.to;
    }
}
