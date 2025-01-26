public class MarkCommand extends Command {
    private final int id;
    private final boolean isMark;

    public MarkCommand(int id, boolean isMark) {
        this.id = id;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        if (this.id < 0 || this.id >= tasks.size()) {
            throw new DuckException("Invalid task number. Use list to view task id");
        }
        Task task = tasks.getTask(id);
        storage.save(tasks.getAllTasks());
        if (isMark) {
            task.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            task.markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + task);
    }
}
