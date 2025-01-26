package duck.command;

import duck.exception.DuckException;
import duck.ui.Storage;
import duck.ui.TaskList;
import duck.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException;

    public boolean isExit() {
        return false;
    }
}
