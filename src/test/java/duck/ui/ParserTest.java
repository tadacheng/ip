package duck.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import duck.command.AddCommand;
import duck.command.Command;
import duck.command.DeleteCommand;
import duck.command.ExitCommand;
import duck.command.HelpCommand;
import duck.command.ListCommand;
import duck.command.MarkCommand;
import duck.exception.DuckException;


public class ParserTest {
    @Test
    public void testParseHelpCommand() throws DuckException {
        Command command = Parser.parse("help");
        assertTrue(command instanceof HelpCommand);
    }

    @Test
    public void testParseListCommand() throws DuckException {
        Command command = Parser.parse("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void testParseTodoCommand() throws DuckException {
        Command command = Parser.parse("todo Read a book");
        assertTrue(command instanceof AddCommand);
    }

    @Test
    public void testParseTodoCommand_emptyDescription_exceptionThrown() {
        DuckException exception = assertThrows(DuckException.class, () -> Parser.parse("todo"));
        assertEquals(new DuckException("Invalid format. Use: todo [description]").getMessage(), exception.getMessage());
    }

    @Test
    public void testParseDeadlineCommand() throws DuckException {
        Command command = Parser.parse("deadline Submit assignment /by 2025-01-01 2359");
        assertTrue(command instanceof AddCommand);
    }

    @Test
    public void testParseDeadlineCommandInvalidFormat() {
        DuckException exception = assertThrows(DuckException.class, () ->
                Parser.parse("deadline Submit assignment"));
        assertEquals(
                new DuckException("Invalid format. Use: deadline [description] "
                        + "/by [Date Time eg. yyyy-MM-dd HHmm]").getMessage(),
                exception.getMessage());
    }

    @Test
    public void testParseEventCommand() throws DuckException {
        Command command = Parser.parse("event Team meeting /from 2025-01-01 1400 /to 2025-01-01 1600");
        assertTrue(command instanceof AddCommand);
    }

    @Test
    public void testParseEventCommand_invalidFormat_exceptionThrown() {
        DuckException exception = assertThrows(DuckException.class, () ->
                Parser.parse("event Team meeting /from 2025-01-01 1400"));
        assertEquals(
                new DuckException("Invalid format. Use: event [description] /from [Start eg. yyyy-MM-dd HHmm] "
                        + "/to [End eg. yyyy-MM-dd HHmm]").getMessage(),
                exception.getMessage());
    }

    @Test
    public void testParseDeleteCommand() throws DuckException {
        Command command = Parser.parse("delete 2");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void testParseDeleteCommand_invalidIndex_exceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Parser.parse("delete a"));
    }

    @Test
    public void testParseMarkCommand() throws DuckException {
        Command command = Parser.parse("mark 3");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParseUnmarkCommand() throws DuckException {
        Command command = Parser.parse("unmark 1");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParseExitCommand() throws DuckException {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void testParseUnknownCommand() {
        DuckException exception = assertThrows(DuckException.class, () -> Parser.parse("unknownCommand"));
        assertEquals(new DuckException("Unknown command: unknownCommand").getMessage(), exception.getMessage());
    }

}
