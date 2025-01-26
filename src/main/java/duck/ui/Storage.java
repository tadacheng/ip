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

public class Storage {
    private final String saveFilePath;

    public Storage(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public List<Task> load() throws DuckException{
        List<Task> tasksList = new ArrayList<>();
        File file = new File(saveFilePath);
        if (!file.exists()) {
            if (file.getParentFile().mkdirs()) { // Creates the file if it doesn't exist
                System.out.println("File created: " + saveFilePath);
            } else {
                throw new DuckException("Failed to create the file: " + saveFilePath);
            }
        }
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
