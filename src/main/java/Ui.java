import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ui {
    private final BufferedReader br;

    public Ui() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void showWelcome() {
        System.out.println("____________________________________________________________\n"
                + "Hello! I'm Duck\nWhat can I do for you?\n"
                + "____________________________________________________________");
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public String readCommand() throws IOException {
        return br.readLine();
    }

    public void showLoadingError() {
        System.out.println("Error loading file. Starting with an empty task list.");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void close() throws IOException {
        br.close();
    }
}
