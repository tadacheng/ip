package duck.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 * Taken from https://se-education.org/guides/tutorials/javaFxPart4.html
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private Duck duck;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Duck2.png"));
    private Image duckImage = new Image(this.getClass().getResourceAsStream("/images/Duck1.png"));

    /**
     * Initialize the window and send welcome message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren()
                .add(DialogBox.getDuckDialog("Hello! I'm Duck\nWhat can I do for you?\n", duckImage));

    }

    /** Injects the Duck instance */
    public void setDuck(Duck d) {
        duck = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing duck's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duck.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDuckDialog(response, duckImage)
        );
        userInput.clear();
        if (response.equals("Bye. Hope to see you again soon!")) {
            Platform.exit();
        }
    }
}

