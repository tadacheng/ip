package duck.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a recurring task that auto-creates a new task when marked as done.
 */
public class Recurring extends Task {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyy, hh:mm a");
    private final LocalDateTime startDate;
    private LocalDateTime nextOccurrence;

    private final String recurrencePattern;

    /**
     * Initializes a recurring task with the specified description, String start date, and recurrence pattern.
     *
     * @param description The description of the recurring task.
     * @param startDate The start date of the recurring task.
     * @param recurrencePattern The pattern for recurrence ("daily", "weekly", "monthly").
     */
    public Recurring(String description, String startDate, String recurrencePattern) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        this.startDate = LocalDateTime.parse(startDate, formatter);
        this.nextOccurrence = LocalDateTime.parse(startDate, formatter);;
        this.recurrencePattern = recurrencePattern;
    }

    /**
     * Initializes a recurring task with the specified description, LocalDateTime start date, and recurrence pattern.
     *
     * @param description The description of the recurring task.
     * @param startDate The start date of the recurring task.
     * @param recurrencePattern The pattern for recurrence ("daily", "weekly", "monthly").
     */
    public Recurring(String description, LocalDateTime startDate, String recurrencePattern) {
        super(description);
        this.startDate = startDate;
        this.nextOccurrence = startDate;
        this.recurrencePattern = recurrencePattern;
    }

    /**
     * Initializes a recurring task with the specified description, LocalDateTime start date, and recurrence pattern.
     *
     * @param description The description of the recurring task.
     * @param startDate The start date of the recurring task.
     * @param nextOccurrence The next occurrence of the task
     * @param recurrencePattern The pattern for recurrence ("daily", "weekly", "monthly").
     */
    public Recurring(String description, LocalDateTime startDate,
                     LocalDateTime nextOccurrence, String recurrencePattern) {
        super(description);
        this.startDate = startDate;
        this.nextOccurrence = nextOccurrence;
        this.recurrencePattern = recurrencePattern;
    }

    /**
     * Marks the recurring task as done and indicates that the next task should be created.
     */
    @Override
    public void markAsDone() {
        super.markAsDone();

        // Calculate the next task's occurrence
        this.nextOccurrence = calculateNextOccurrence();
    }

    /**
     * Calculates the next occurrence based on the recurrence pattern.
     *
     * @return The next occurrence of the task.
     */
    public LocalDateTime getNextOccurrence() {
        return this.nextOccurrence;
    }


    /**
     * Calculates the next occurrence based on the recurrence pattern.
     *
     * @return The next occurrence of the task.
     */
    private LocalDateTime calculateNextOccurrence() {
        switch (this.recurrencePattern.toLowerCase()) {
        case "day":
            return this.nextOccurrence.plusDays(1);
        case "week":
            return this.nextOccurrence.plusWeeks(1);
        case "month":
            return this.nextOccurrence.plusMonths(1);
        default:
            System.out.println("Unsupported recurrence pattern.");
            return this.nextOccurrence; // Default to the same time if unsupported
        }
    }


    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    /**
     * Returns a string representation of the task, including its status icon and description.
     *
     * @return A string representing the event task.
     */
    @Override
    public String toString() {
        return "[R]" + super.toString() + " (every " + this.recurrencePattern + " at: "
                + this.startDate.format(DATE_TIME_FORMATTER) + ")";
    }

    /**
     * Converts the event task to a format suitable for saving to a file.
     *
     * @return A string representation of the event task in file format.
     */
    @Override
    public String toFileFormat() {
        return "R | " + (this.isDone ? "1" : "0") + " | "
                + this.description + " | " + this.startDate + " | "
                + this.nextOccurrence + " | " + this.recurrencePattern;
    }
}
