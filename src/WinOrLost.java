import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WinOrLost {
    public static void gameOver(String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Over");
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font: 20 arial;");
        Button OK = new Button("OK");
        OK.setPrefSize(80, 30);
        OK.setOnMouseClicked(event -> window.close());
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, OK);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 380, 250);
        window.setScene(scene);
        window.showAndWait();

    }
}
