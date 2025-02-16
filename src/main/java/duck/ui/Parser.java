package duck.ui;

import java.util.regex.Pattern;

import duck.command.AddCommand;
import duck.command.Command;
import duck.command.DeleteCommand;
import duck.command.ExitCommand;
import duck.command.FindCommand;
import duck.command.HelpCommand;
import duck.command.ListCommand;
import duck.command.MarkCommand;
import duck.exception.DuckException;
import duck.task.Deadline;
import duck.task.Event;
import duck.task.Recurring;
import duck.task.Todo;

/**
 * Parses user input and returns the corresponding Command object.
 * This class processes various command strings and generates commands such as Add, Delete, Mark, etc.
 */
public class Parser {

    private static boolean isValidDateTime(String s) {
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s([01]\\d|2[0-3])[0-5]\\d$";
        return Pattern.matches(regex, s);
    }

    /**
     * Parses the given command string and returns the corresponding Command object.
     *
     * @param fullCommand The full command string input by the user.
     * @return The corresponding Command object based on the parsed input.
     * @throws DuckException If the command is invalid or incorrectly formatted.
     */
    public static Command parse(String fullCommand) throws DuckException {
        assert fullCommand != null && !fullCommand.isBlank() : "Input command should not be null or blank";
        String[] parts = fullCommand.split(" ", 2);
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";
        return switch (commandWord) {
        case "help" -> new HelpCommand();
        case "list" -> new ListCommand();
        case "todo" -> {
            if (arguments.isEmpty()) {
                throw new DuckException("Invalid format. Use: todo [description]");
            }
            yield new AddCommand(new Todo(arguments));
        }
        case "deadline" -> {
            String[] details = arguments.split(" /by ");
            if (details.length < 2) {
                throw new DuckException("Invalid format. "
                        + "Use: deadline [description] /by [Date Time eg. yyyy-MM-dd HHmm]");
            }
            if (!isValidDateTime(details[1])) {
                throw new DuckException("Invalid Date Time format. "
                        + "Use: deadline [description] /by [Date Time eg. yyyy-MM-dd HHmm]");
            }
            yield new AddCommand(new Deadline(details[0], details[1]));
        }
        case "event" -> {
            String[] details = arguments.split(" /from ");
            if (details.length < 2 || !details[1].contains(" /to ")) {
                throw new DuckException("Invalid format. "
                        + "Use: event [description] /from [Start eg. yyyy-MM-dd HHmm] "
                        + "/to [End eg. yyyy-MM-dd HHmm]");
            }
            String[] times = details[1].split(" /to ");
            if (!isValidDateTime(times[0]) || !isValidDateTime(times[1])) {
                throw new DuckException("Invalid DateTime format. "
                        + "Use: event [description] /from [Start eg. yyyy-MM-dd HHmm] "
                        + "/to [End eg. yyyy-MM-dd HHmm]");
            }
            yield new AddCommand(new Event(details[0], times[0], times[1]));
        }
        case "recurring" -> {
            String[] details = arguments.split("/at ");
            if (details.length < 2 || !details[1].contains(" /every ")) {
                throw new DuckException("Invalid format. "
                        + "Use: recurring [description] /at [Start eg. yyyy-MM-dd HHmm] "
                        + "/every [Pattern, day, week, month]");
            }
            String[] times = details[1].split(" /every ");
            if (!isValidDateTime(times[0]) || !isValidDateTime(times[1])) {
                throw new DuckException("Invalid Date Time format. "
                        + "Use: recurring [description] /at [Start eg. yyyy-MM-dd HHmm] "
                        + "/every [Pattern, day, week, month]");
            }
            yield new AddCommand(new Recurring(details[0], times[0], times[1]));
        }
        case "delete" -> new DeleteCommand(Integer.parseInt(arguments) - 1);
        case "mark" -> new MarkCommand(Integer.parseInt(arguments) - 1, true);
        case "unmark" -> new MarkCommand(Integer.parseInt(arguments) - 1, false);
        case "find" -> new FindCommand(arguments);
        case "bye" -> new ExitCommand();
        default -> throw new DuckException("Unknown command: " + commandWord);
        };
    }
}
