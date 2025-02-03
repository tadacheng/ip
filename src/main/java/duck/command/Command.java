package duck.command;

import duck.exception.DuckException;
import duck.ui.Storage;
import duck.ui.TaskList;
import duck.ui.Ui;

/**
 * Represents an abstract command that can be executed in the application.
 */
public abstract class Command {
    protected static final String CMD_NOT_EXE_RESPONSE = "Command has not been executed";
    protected boolean hasExecuted = false;
    protected String executedResponse;

    /**
     * Executes the command with the given task list, user interface, and storage.
     * This method is intended to be implemented by subclasses to define specific command behavior.
     *
     * @param tasks The current list of tasks that the command will operate on.
     * @param ui The user interface to interact with the user.
     * @param storage The storage used to save or load tasks.
     * @throws DuckException If an error occurs while executing the command.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException;

    /**
     * Get the string response from the result of the command.
     * @return String response of command result.
     */
    public String getString() {
        if (!this.hasExecuted) {
            return CMD_NOT_EXE_RESPONSE;
        } else {
            return this.executedResponse;
        }
    }

    /**
     * Determines if the command is an exit command.
     * This method can be overridden by subclasses to return true if the command is meant to exit the program.
     *
     * @return false by default.
     */
    public boolean isExit() {
        return false;
    }

}
