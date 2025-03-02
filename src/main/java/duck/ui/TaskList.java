package duck.ui;

import java.util.ArrayList;
import java.util.List;

import duck.exception.DuckException;
import duck.task.Task;

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
        assert tasks != null : "Task list should not be null";
        this.tasks = tasks;
    }


    /**
     * Adds a task to the list.
     *
     * @param task The task to be added to the list.
     */
    public void addTask(Task task) {
        assert task != null : "Task to be added should not be null";
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
        assert id >= 0 && id < tasks.size() : "Task ID out of bounds: " + id;
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

    /**
     * Returns a list of Task with keyword in its descriptions.
     *
     * @param keyword The keyword to be searched in the task list.
     * @return The list of found task with keyword in description.
     */
    public List<Task> findTasks(String keyword) {
        assert keyword != null && !keyword.isBlank() : "Search keyword should not be null or empty";
        return tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .toList();
    }
}
