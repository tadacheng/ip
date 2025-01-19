import java.io.*;
import java.util.ArrayList;

public class Duck {
    public static void main(String[] args) throws IOException {
        String divider = "____________________________________________________________\n";
        String greeting = divider + "Hello! I'm Duck\nWhat can I do for you?\n" + divider;
        String goodbye = divider + "Bye. Hope to see you again soon!\n" + divider;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(greeting);
        String user_input;
        ArrayList<Task> tasks_list = new ArrayList<>();

        while (true) {
            user_input = br.readLine();
            if (user_input.equals("bye")) {
                System.out.println(goodbye);
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
                Task task = tasks_list.get(Integer.parseInt(user_input.substring(5)) - 1);
                task.markAsDone();
                System.out.println(divider + "Nice! I've marked this task as done:");
                System.out.println("  " + task);
                System.out.print(divider);
            } else if (user_input.startsWith("unmark ")) {
                Task task = tasks_list.get(Integer.parseInt(user_input.substring(7)) - 1);
                task.markAsNotDone();
                System.out.println(divider + "OK, I've marked this task as not done yet:");
                System.out.println("  " + task);
                System.out.print(divider);
            } else if (user_input.startsWith("todo ")){
                String task_description = user_input.substring(5);
                Task todo = new Todo(task_description);
                tasks_list.add(todo);
                System.out.println(divider + "Got it. I've added this task:\n");
                System.out.println("  " + todo);
                System.out.println("Now you have " + tasks_list.size() + " tasks in the list.");
                System.out.print(divider);
            } else if (user_input.startsWith("deadline ")){
                String[] task_description_by = user_input.substring(9).split(" /by ");
                Task deadline = new Deadline(task_description_by[0], task_description_by[1]);
                tasks_list.add(deadline);
                System.out.println(divider + "Got it. I've added this task:");
                System.out.println("  " + deadline);
                System.out.println("Now you have " + tasks_list.size() + " tasks in the list.");
                System.out.print(divider);
            } else if (user_input.startsWith("event ")){
                String[] task_description_time = user_input.substring(6).split(" /from ");
                String[] time_from_to = task_description_time[1].split(" /to ");
                Task event = new Event(task_description_time[1], time_from_to[0], time_from_to[1]);
                tasks_list.add(event);
                System.out.println(divider + "Got it. I've added this task:");
                System.out.println("  " + event);
                System.out.println("Now you have " + tasks_list.size() + " tasks in the list.");
                System.out.print(divider);
            }

        }
        br.close();
    }
}
