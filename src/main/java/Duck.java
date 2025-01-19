import java.util.ArrayList;
import java.util.Scanner;

public class Duck {
    public static void main(String[] args) {
        String divider = "____________________________________________________________\n";
        String greeting = divider + "Hello! I'm Duck\nWhat can I do for you?\n" + divider;
        String goodbye = divider + "Bye. Hope to see you again soon!\n" + divider;

        Scanner sc = new Scanner(System.in);
        System.out.println(greeting);
        String user_input;
        ArrayList<String> tasks = new ArrayList<>();

        while (true) {
            user_input = sc.nextLine().trim();
            if (user_input.equals("bye")) {
                System.out.println(goodbye);
                break;
            } else if (user_input.equals("list")) {
                System.out.print(divider);
                if (tasks.isEmpty()) {
                    System.out.println("No tasks in the list.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }
                System.out.print(divider);
            } else {
                tasks.add(user_input);
                System.out.println(divider + "added: " + user_input + "\n" + divider);
            }
        }
    }
}
