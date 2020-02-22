import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExitGame {
    public static void exit(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(290);
        window.setMinHeight(200);
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font: 20 arial;");
        Button Yes = new Button("Yes");
        Button No = new Button("No");
        Button Cancel = new Button("Cancel");

        Yes.setOnMouseClicked(event -> {
            Hangman.save();
            Platform.exit();
            System.exit(0);
        });
        Cancel.setOnMouseClicked(event -> window.close());

        No.setOnMouseClicked(event -> {
            Platform.exit();
            System.exit(0);
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(Yes, No, Cancel);
        hBox.setSpacing(30);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, hBox);
        vBox.setSpacing(35);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 400, 250);
        window.setScene(scene);
        window.showAndWait();
    }
}
