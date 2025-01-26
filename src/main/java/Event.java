import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyy, hh:mm a");

    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        this.from = LocalDateTime.parse(from, formatter);
        this.to = LocalDateTime.parse(to, formatter);
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }


    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(DATE_TIME_FORMATTER)
                + " to: " + this.to.format(DATE_TIME_FORMATTER) + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.from + " | " + this.to;
    }
}
