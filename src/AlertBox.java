import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font: 18 arial;");
        Button Yes = new Button("Yes");
        Button No = new Button("No");
        Button Cancel = new Button("Cancel");

        Yes.setOnMouseClicked(event -> {
            Hangman.save();
            Hangman.arrayList.clear();
            Hangman.startPlay.setDisable(false);
            Hangman.borderPane.getBottom().setVisible(true);
            Hangman.borderPane.setRight(null);
            Hangman.borderPane.setLeft(null);
            Hangman.main.getChildren().get(1).setVisible(false);
            Hangman.hangManBody.clear();
            Hangman.index = 0;
            Hangman.alphabet.clear();
            window.close();
        });
        Cancel.setOnMouseClicked(event -> {
            Hangman.button2.setDisable(true);
            window.close();
        });
        // No
        No.setOnMouseClicked(event -> {
            //Hangman.vocab.clear();
            Hangman.button2.setDisable(true);
            Hangman.arrayList.clear();
            Hangman.startPlay.setDisable(false);
            Hangman.borderPane.getBottom().setVisible(true);
            Hangman.borderPane.setRight(null);
            Hangman.borderPane.setLeft(null);
            Hangman.main.getChildren().get(1).setVisible(false);
            Hangman.hangManBody.clear();
            Hangman.index = 0;
            Hangman.alphabet.clear();
            window.close();
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
