import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


public class TargetLetter extends StackPane {
    private Label text;

    public TargetLetter(Label text) {
        this.text = text;
    }

    public Label getLabel() {
        return text;
    }
}
