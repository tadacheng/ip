package duck.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duck.exception.DuckException;
import duck.task.Deadline;
import duck.task.Event;
import duck.task.Task;
import duck.task.Todo;

public class StorageTest {
    private final Path testFilePath = Paths.get("data", "duck_test.txt");

    private Storage storage;

    @BeforeEach
    public void setUp() throws InterruptedException {
        storage = new Storage(testFilePath.toString());
        // Ensure test file is clean before every test
        File testFile = testFilePath.toFile();
        System.gc(); // Suggests garbage collection to release file locks
        Thread.sleep(100); // Give the system time to close handles
        if (testFile.exists()) {
            assertTrue(testFile.delete());
        }
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        // Clean up test file after tests
        File testFile = testFilePath.toFile();
        System.gc(); // Suggests garbage collection to release file locks
        Thread.sleep(100); // Give the system time to close handles
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
        tasksToSave.add(
                new Deadline("Deadline task",
                        LocalDateTime.of(2025, 1, 30, 12, 0)));
        tasksToSave.add(
                new Event("Event task",
                        LocalDateTime.of(2025, 2, 1, 14, 0),
                        LocalDateTime.of(2025, 2, 1, 16, 0)));

        storage.save(tasksToSave);

        List<Task> loadedTasks = storage.load();
        assertEquals(tasksToSave.size(), loadedTasks.size());
        for (int i = 0; i < tasksToSave.size(); i++) {
            assertEquals(tasksToSave.get(i).toString(), loadedTasks.get(i).toString());
        }
    }

    @Test
    public void testLoad_invalidFileContent_exceptionThrown() throws IOException {
        File testFile = testFilePath.toFile();
        testFile.getParentFile().mkdir();
        testFile.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("X | 0 | Invalid Task");
        } catch (Exception e) {
            fail("Failed to write invalid content for testing");
        }

        assertThrows(DuckException.class, () -> storage.load());
    }

    @Test
    public void testLoad_withCorruptFormat_exceptionThrown() throws IOException {
        File testFile = testFilePath.toFile();
        testFile.getParentFile().mkdir();
        testFile.createNewFile();
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
