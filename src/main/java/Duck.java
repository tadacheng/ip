import java.io.*;
import java.util.ArrayList;

public class Duck {
    private static final String DIVIDER = "____________________________________________________________\n";
    private static ArrayList<Task> tasksList = new ArrayList<>();
    private static final String SAVE_FILE_PATH = "./data/duck.txt";

    private static void loadTasksFromSave() throws IOException {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdirs(); // Creates the directory if it doesn't exist
        }
        File saveFile = new File(SAVE_FILE_PATH);
        if (!saveFile.exists()) {
            saveFile.createNewFile(); // Creates the file if it doesn't exist
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(" \\| ");
                Task task = switch (taskData[0]) {
                    case "T" -> new Todo(taskData[2]);
                    case "D" -> new Deadline(taskData[2], taskData[3]);
                    case "E" -> new Event(taskData[2], taskData[3], taskData[4]);
                    default -> throw new DuckException("Invalid task type in file.");
                };
                if (taskData[1].equals("1")) task.markAsDone();
                tasksList.add(task);
            }
        } catch (IOException | DuckException e) {
            System.out.println("Failed to load tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(DIVIDER + "Hello! I'm Duck\nWhat can I do for you?\n" + DIVIDER);
        String userInput;
        loadTasksFromSave();

        while (true) {
            userInput = br.readLine();
            try {
                if (userInput.equals("help")) {
                    System.out.print(DIVIDER);
                    System.out.println("""
                            Usage:\s
                            list - Show tasklist
                            bye - Quit
                            todo [description] - Create Todo Task
                            deadline [description] /by [date/time] - Create Deadline Task
                            event [description] /from [start] /to [end] - Create Event Task
                            mark [task_id] - Set Task as Done
                            unmark [task_id] - Set Task as Not Done
                            delete [task_id] - Delete Task""");
                    System.out.print(DIVIDER);
                } else if (userInput.equals("bye")) {
                    System.out.println(DIVIDER + "Bye. Hope to see you again soon!\n" + DIVIDER);
                    break;
                } else if (userInput.equals("list")) {
                    System.out.print(DIVIDER);
                    if (tasksList.isEmpty()) {
                        System.out.println("No tasks in the list.");
                    } else {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasksList.size(); i++) {
                            Task task = tasksList.get(i);
                            System.out.println((i + 1) + "." + task);
                        }
                    }
                    System.out.print(DIVIDER);
                } else if (userInput.startsWith("mark ")) {
                    int task_id = Integer.parseInt(userInput.substring(5)) - 1;
                    if (task_id < 0 || task_id >= tasksList.size()) {
                        throw new DuckException("Invalid task number. Use list to view task id");
                    }
                    Task task = tasksList.get(task_id);
                    task.markAsDone();

                    System.out.println(DIVIDER + "Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                    System.out.print(DIVIDER);

                } else if (userInput.startsWith("unmark ")) {
                    int task_id = Integer.parseInt(userInput.substring(7)) - 1;
                    if (task_id < 0 || task_id >= tasksList.size()) {
                        throw new DuckException("Invalid task number. Use list to view task id");
                    }
                    Task task = tasksList.get(task_id);
                    task.markAsNotDone();

                    System.out.println(DIVIDER + "OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                    System.out.print(DIVIDER);

                } else if (userInput.startsWith("delete ")) {
                    int task_id = Integer.parseInt(userInput.substring(7)) - 1;
                    if (task_id < 0 || task_id >= tasksList.size()) {
                        throw new DuckException("Invalid task number. Use list to view task id");
                    }
                    Task task = tasksList.get(task_id);
                    tasksList.remove(task_id);
                    System.out.println(DIVIDER + "Noted. I've removed this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasksList.size() + " tasks in the list.");
                    System.out.print(DIVIDER);

                } else if (userInput.startsWith("todo ")) {
                    String taskDescription = userInput.substring(5).trim();
                    if (taskDescription.isEmpty()) {
                        throw new DuckException("Invalid format. Use: todo [description]");
                    }
                    Task todo = new Todo(taskDescription);
                    tasksList.add(todo);

                    System.out.println(DIVIDER + "Got it. I've added this task:\n");
                    System.out.println("  " + todo);
                    System.out.println("Now you have " + tasksList.size() + " tasks in the list.");
                    System.out.print(DIVIDER);

                } else if (userInput.startsWith("deadline ")) {
                    String[] taskDescriptionBy = userInput.substring(9).split(" /by ");
                    if (taskDescriptionBy.length < 2) {
                        throw new DuckException("Invalid format. Use: deadline [description] /by [date/time]");
                    }
                    Task deadline = new Deadline(taskDescriptionBy[0], taskDescriptionBy[1]);
                    tasksList.add(deadline);

                    System.out.println(DIVIDER + "Got it. I've added this task:");
                    System.out.println("  " + deadline);
                    System.out.println("Now you have " + tasksList.size() + " tasks in the list.");
                    System.out.print(DIVIDER);

                } else if (userInput.startsWith("event ")) {
                    String[] taskDescriptionTime = userInput.substring(6).split(" /from ");
                    if (taskDescriptionTime.length < 2 || !taskDescriptionTime[1].contains(" /to ")) {
                        throw new DuckException("Invalid format. Use: event [description] /from [start] /to [end]");
                    }
                    String[] timeFromTo = taskDescriptionTime[1].split(" /to ");
                    Task event = new Event(taskDescriptionTime[1], timeFromTo[0], timeFromTo[1]);
                    tasksList.add(event);

                    System.out.println(DIVIDER + "Got it. I've added this task:");
                    System.out.println("  " + event);
                    System.out.println("Now you have " + tasksList.size() + " tasks in the list.");
                    System.out.print(DIVIDER);

                } else {
                    throw new DuckException("Invalid Command. Enter help to display command usage!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Task id must be an integer.");
            } catch (DuckException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(DIVIDER + "An unexpected error occurred: " + e.getMessage() + DIVIDER);
            }
        }
        br.close();
    }
}
