import java.util.Scanner;

public class Duck {
    public static void main(String[] args) {
        String divider = "____________________________________________________________\n";
        String greeting =  divider + "Hello! I'm Duck\nWhat can I do for you?\n" + divider;
        String goodbye = divider + "Bye. Hope to see you again soon!\n" + divider;

        Scanner sc = new Scanner(System.in);
        System.out.println(greeting);
        String user_input;
        while (true) {
            user_input = sc.nextLine();
            if (user_input.equals("bye")) break;
            System.out.println(divider + user_input + "\n" + divider);
        }
        System.out.println(goodbye);
    }
}
