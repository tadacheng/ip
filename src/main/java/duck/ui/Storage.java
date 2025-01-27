package duck.ui;

import duck.exception.DuckException;
import duck.task.Deadline;
import duck.task.Event;
import duck.task.Task;
import duck.task.Todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the loading and saving of tasks from/to a specified file.
 * The tasks are saved in a file and can be reloaded upon starting the application.
 */
public class Storage {
    private final String saveFilePath;

    /**
     * Initializes the Storage with a given file path to load and save tasks.
     *
     * @param saveFilePath The path of the file where tasks are stored.
     */
    public Storage(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    /**
     * Loads tasks from the storage file.
     * If the file or directory doesn't exist, it will be created.
     * The file content is parsed to recreate the list of tasks.
     *
     * @return A list of tasks loaded from the file.
     * @throws DuckException If an error occurs during file reading or if the file format is invalid.
     */
    public List<Task> load() throws DuckException {
        List<Task> tasksList = new ArrayList<>();

        // Check if file and parent directory exists
        File file = new File(saveFilePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new DuckException("Failed to create the directory: " + parentDir.getPath());
        }
        if (!file.exists()) {
            try {
                if (file.createNewFile()) { // Creates the file if it doesn't exist
                    System.out.println("File created: " + saveFilePath);
                } else {
                    throw new DuckException("Failed to create the file: " + saveFilePath);
                }
            } catch (IOException e) {
                throw new DuckException("Failed to create the file: " + saveFilePath);
            }
        }

        // Load content from file and populate tasks list
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(" \\| ");
                Task task = switch (taskData[0]) {
                    case "T" -> new Todo(taskData[2]);
                    case "D" -> new Deadline(taskData[2], LocalDateTime.parse(taskData[3]));
                    case "E" -> new Event(taskData[2], LocalDateTime.parse(taskData[3]), LocalDateTime.parse(taskData[4]));
                    default -> throw new DuckException("Invalid task type in file.");
                };
                if (taskData[1].equals("1")) task.markAsDone();
                tasksList.add(task);
            }
        } catch (IOException | DuckException e) {
            throw new DuckException("Failed to load tasks: \n" + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DuckException("File content not in the expected format");
        }
        return tasksList;
    }

    /**
     * Saves the given list of tasks to the storage file.
     *
     * @param tasksList The list of tasks to be saved to the file.
     * @throws DuckException If an error occurs during file writing.
     */
    public void save(List<Task> tasksList) throws DuckException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath))) {
            for (Task task : tasksList) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DuckException("Failed to save tasks: " + e.getMessage());
        }
    }

}
