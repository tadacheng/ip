import java.io.*;
import java.util.ArrayList;

public class Duck {
    public static void main(String[] args) throws IOException {
        String divider = "____________________________________________________________\n";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(divider + "Hello! I'm Duck\nWhat can I do for you?\n" + divider);
        String user_input;
        ArrayList<Task> tasks_list = new ArrayList<>();

        while (true) {
            user_input = br.readLine();
            try {
                if (user_input.equals("help")) {
                    System.out.print(divider);
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
                    System.out.print(divider);
                } else if (user_input.equals("bye")) {
                    System.out.println(divider + "Bye. Hope to see you again soon!\n" + divider);
                    break;
                } else if (user_input.equals("list")) {
                    System.out.print(divider);
                    if (tasks_list.isEmpty()) {
                        System.out.println("No tasks in the list.");
                    } else {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks_list.size(); i++) {
                            Task task = tasks_list.get(i);
                            System.out.println((i + 1) + "." + task);
                        }
                    }
                    System.out.print(divider);
                } else if (user_input.startsWith("mark ")) {
                    int task_id = Integer.parseInt(user_input.substring(5)) - 1;
                    if (task_id < 0 || task_id >= tasks_list.size()) {
                        throw new DuckException("Invalid task number. Use list to view task id");
                    }
                    Task task = tasks_list.get(task_id);
                    task.markAsDone();

                    System.out.println(divider + "Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                    System.out.print(divider);

                } else if (user_input.startsWith("unmark ")) {
                    int task_id = Integer.parseInt(user_input.substring(7)) - 1;
                    if (task_id < 0 || task_id >= tasks_list.size()) {
                        throw new DuckException("Invalid task number. Use list to view task id");
                    }
                    Task task = tasks_list.get(task_id);
                    task.markAsNotDone();

                    System.out.println(divider + "OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                    System.out.print(divider);

                } else if (user_input.startsWith("delete ")) {
                    int task_id = Integer.parseInt(user_input.substring(7)) - 1;
                    if (task_id < 0 || task_id >= tasks_list.size()) {
                        throw new DuckException("Invalid task number. Use list to view task id");
                    }
                    Task task = tasks_list.get(task_id);
                    tasks_list.remove(task_id);
                    System.out.println(divider + "Noted. I've removed this task:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks_list.size() + " tasks in the list.");
                    System.out.print(divider);

                } else if (user_input.startsWith("todo ")) {
                    String task_description = user_input.substring(5).trim();
                    if (task_description.isEmpty()) {
                        throw new DuckException("Invalid format. Use: todo [description]");
                    }
                    Task todo = new Todo(task_description);
                    tasks_list.add(todo);

                    System.out.println(divider + "Got it. I've added this task:\n");
                    System.out.println("  " + todo);
                    System.out.println("Now you have " + tasks_list.size() + " tasks in the list.");
                    System.out.print(divider);

                } else if (user_input.startsWith("deadline ")) {
                    String[] task_description_by = user_input.substring(9).split(" /by ");
                    if (task_description_by.length < 2) {
                        throw new DuckException("Invalid format. Use: deadline [description] /by [date/time]");
                    }
                    Task deadline = new Deadline(task_description_by[0], task_description_by[1]);
                    tasks_list.add(deadline);

                    System.out.println(divider + "Got it. I've added this task:");
                    System.out.println("  " + deadline);
                    System.out.println("Now you have " + tasks_list.size() + " tasks in the list.");
                    System.out.print(divider);

                } else if (user_input.startsWith("event ")) {
                    String[] task_description_time = user_input.substring(6).split(" /from ");
                    if (task_description_time.length < 2 || !task_description_time[1].contains(" /to ")) {
                        throw new DuckException("Invalid format. Use: event [description] /from [start] /to [end]");
                    }
                    String[] time_from_to = task_description_time[1].split(" /to ");
                    Task event = new Event(task_description_time[1], time_from_to[0], time_from_to[1]);
                    tasks_list.add(event);

                    System.out.println(divider + "Got it. I've added this task:");
                    System.out.println("  " + event);
                    System.out.println("Now you have " + tasks_list.size() + " tasks in the list.");
                    System.out.print(divider);

                } else {
                    throw new DuckException("Error: The command is invalid. Enter help to display command usage!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Task id must be an integer.");
            } catch (DuckException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(divider + "An unexpected error occurred: " + e.getMessage() + divider);
            }
        }
        br.close();
    }
}
