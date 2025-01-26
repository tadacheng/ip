package duck.ui;

import duck.exception.DuckException;
import duck.task.Task;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a list of tasks.
 * Provides methods to add, delete, and retrieve tasks from the list.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Initializes an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Initializes the task list with a predefined list of tasks.
     *
     * @param tasks A list of tasks to initialize the task list.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }


    /**
     * Adds a task to the list.
     *
     * @param task The task to be added to the list.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Deletes a task from the list by its id.
     *
     * @param id The ID of the task to be deleted.
     * @return The deleted task.
     * @throws DuckException If the task ID is invalid (out of bounds).
     */
    public Task deleteTask(int id) throws DuckException {
        if (id < 0 || id >= this.tasks.size()) {
            throw new DuckException("Invalid task number. Use list to view task id");
        }
        return tasks.remove(id);
    }


    /**
     * Retrieves a task from the list by its ID.
     *
     * @param id The ID of the task to be retrieved.
     * @return The task with the specified ID.
     */
    public Task getTask(int id) {
        return tasks.get(id);
    }

    /**
     * Retrieves all tasks in the list.
     *
     * @return A list of all tasks.
     */
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Retrieves the size of the task list.
     *
     * @return The number of tasks in the list.
     */
    public int size() {
        return tasks.size();
    }
}
