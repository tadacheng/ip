package duck.ui;

import duck.exception.DuckException;
import duck.task.Deadline;
import duck.task.Event;
import duck.task.Task;
import duck.task.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {
    private static final String TEST_FILE_PATH = "./data/duck_test.txt";
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage(TEST_FILE_PATH);
        // Ensure test file is clean before every test
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            assertTrue(testFile.delete());
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up test file after tests
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            assertTrue(testFile.delete());
        }
    }

    @Test
    public void testLoadEmptyFile() throws DuckException {
        List<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void testSaveAndLoadTasks() throws DuckException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("Todo task"));
        tasksToSave.add(new Deadline("Deadline task", LocalDateTime.of(2025, 1, 30, 12, 0)));
        tasksToSave.add(new Event("Event task", LocalDateTime.of(2025, 2, 1, 14, 0), LocalDateTime.of(2025, 2, 1, 16, 0)));

        storage.save(tasksToSave);

        List<Task> loadedTasks = storage.load();
        assertEquals(tasksToSave.size(), loadedTasks.size());
        for (int i = 0; i < tasksToSave.size(); i++) {
            assertEquals(tasksToSave.get(i).toString(), loadedTasks.get(i).toString());
        }
    }

    @Test
    public void testLoad_invalidFileContent_exceptionThrown() {
        File testFile = new File(TEST_FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("X | 0 | Invalid Task");
        } catch (Exception e) {
            fail("Failed to write invalid content for testing");
        }

        assertThrows(DuckException.class, () -> storage.load());
    }

    @Test
    public void testLoad_withCorruptFormat_exceptionThrown() {
        File testFile = new File(TEST_FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("T | 0"); // Incomplete task data
        } catch (Exception e) {
            fail("Failed to write invalid content for testing");
        }

        assertThrows(DuckException.class, () -> storage.load());
    }

    @Test
    public void testSave_invalidPath_exceptionThrown() {
        Storage invalidStorage = new Storage("invalid_path/duck_test.txt");
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Todo task"));

        DuckException exception = assertThrows(DuckException.class, () -> invalidStorage.save(tasks));
        assertTrue(exception.getMessage().contains("Failed to save tasks:"));
    }
}
