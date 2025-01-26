package duck.ui;

import duck.exception.DuckException;
import duck.task.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
class TaskSUT extends Task {
    public TaskSUT(String description) {
        super(description);
    }

    @Override
    public String toFileFormat() {
        return (this.isDone ? "1" : "0") + " | " + this.description;
    }
}
public class TaskListTest {
    @Test
    public void testAddTask_success() {
        TaskList taskList = new TaskList();
        Task task = new TaskSUT("Test Task");
        taskList.addTask(task);
        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void testDeleteTask_validId_success() throws DuckException {
        TaskList taskList = new TaskList();
        Task task = new TaskSUT("Test Task");
        taskList.addTask(task);
        Task deletedTask = taskList.deleteTask(0);
        assertEquals(task, deletedTask);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testDeleteTask_invalidId_exceptionThrown() {
        TaskList taskList = new TaskList();
        try {
            taskList.deleteTask(0);
            fail();
        } catch (DuckException e) {
            assertEquals(new DuckException("Invalid task number. Use list to view task id").getMessage(), e.getMessage());
        }
    }

    @Test
    public void testGetTask_validId_success() {
        Task task = new TaskSUT("Test Task");
        TaskList taskList = new TaskList();
        taskList.addTask(task);
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void testGetTask_invalidId_exceptionThrown() {
        TaskList taskList = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.getTask(0);
        });
    }

    @Test
    public void testSize() {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.size());
        taskList.addTask(new TaskSUT("Task 1"));
        taskList.addTask(new TaskSUT("Task 2"));
        assertEquals(2, taskList.size());
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new TaskSUT("Task 1");
        Task task2 = new TaskSUT("Task 2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        TaskList taskList = new TaskList(tasks);
        assertEquals(tasks, taskList.getAllTasks());
    }
}
