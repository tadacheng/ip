import java.io.*;
import java.util.ArrayList;

public class Duck {
    public static void main(String[] args) throws IOException {
        String divider = "____________________________________________________________\n";
        String greeting = divider + "Hello! I'm Duck\nWhat can I do for you?\n" + divider;
        String goodbye = divider + "Bye. Hope to see you again soon!\n" + divider;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(greeting);
        String[] user_input;
        ArrayList<Task> tasks_list = new ArrayList<>();

        while (true) {
            user_input = br.readLine().split(" ");
            if (user_input[0].equals("bye")) {
                System.out.println(goodbye);
                break;
            } else if (user_input[0].equals("list")) {
                System.out.print(divider);
                if (tasks_list.isEmpty()) {
                    System.out.println("No tasks in the list.");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks_list.size(); i++) {
                        Task task = tasks_list.get(i);
                        System.out.println((i + 1) + ".[" + task.getStatusIcon() + "] " + task.getDescription());
                    }
                }
                System.out.print(divider);
            } else if (user_input[0].equals("mark")) {

                Task task = tasks_list.get(Integer.parseInt(user_input[1]) - 1);
                task.markAsDone();
                System.out.println(divider + "Nice! I've marked this task as done:");
                System.out.println("  [" + task.getStatusIcon() + "] " + task.getDescription());
                System.out.print(divider);
            } else if (user_input[0].equals("unmark")) {
                Task task = tasks_list.get(Integer.parseInt(user_input[1]) - 1);
                task.markAsNotDone();
                System.out.println(divider + "OK, I've marked this task as not done yet:");
                System.out.println("  [" + task.getStatusIcon() + "] " + task.getDescription());
                System.out.print(divider);
            } else {
                tasks_list.add(new Task(String.join(" ", user_input)));
                System.out.println(divider + "added: " + String.join(" ", user_input) + "\n" + divider);
            }

        }
        br.close();
    }
}
