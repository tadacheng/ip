package duck.ui;

import duck.exception.DuckException;
import duck.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task deleteTask(int id) throws DuckException {
        if (id < 0 || id >= this.tasks.size()) {
            throw new DuckException("Invalid task number. Use list to view task id");
        }
        return tasks.remove(id);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }
}
