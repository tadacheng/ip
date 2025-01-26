public class DeleteCommand extends Command {
    private final int id;

    public DeleteCommand(int id) {
        this.id = id;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        Task removedTask = tasks.deleteTask(this.id);
        storage.save(tasks.getAllTasks());
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }
}
