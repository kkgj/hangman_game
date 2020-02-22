import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman extends Application {
    static ArrayList<TargetLetter> arrayList = new ArrayList<>();
    static ArrayList<Button> alphabet = new ArrayList<>();
    static ArrayList<Shape> hangManBody = new ArrayList<>();
    static int index = 0;
    static String word;
    static BorderPane borderPane;
    static Button startPlay;
    static VBox right;
    static VBox main;
    static int remainNum = 10; // x
    static Label remainGuess; // x
    static Stage primaryStage;
    static Button button2;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        borderPane = new BorderPane();
        Image Start = new Image("New.png");
        Image Save = new Image("Save.png");
        Image Load = new Image("Load.png");
        Image Exit = new Image("Exit.png");
        ImageView load = new ImageView(Load);
        ImageView save1 = new ImageView(Save);
        ImageView start = new ImageView(Start);
        ImageView exit = new ImageView(Exit);

        startPlay = new Button("Start Playing");
        HBox bottom = new HBox();
        bottom.getChildren().add(startPlay);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10, 0, 10, 0));
        bottom.setStyle("-fx-background-color: #dcdcdc;");

        button2 = new Button("Save", save1);
        button2.setDisable(true);
        button2.setOnMouseClicked(event -> {
            save();
        });
        Button button = new Button("New", start);
        Button button1 = new Button("Load", load); //  load load load load load
        Button button3 = new Button("Exit", exit);
        borderPane.setBottom(bottom);
        borderPane.getBottom().setVisible(false);

        button1.setOnMouseClicked(event -> {
            startPlay.setDisable(true);
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file != null)
                load(file);
            else
                startPlay.setDisable(false);
        });

        button.setOnMouseClicked(event -> {
            if (borderPane.getRight() == null)
                borderPane.getBottom().setVisible(true);
            else
                AlertBox.display("New", "Would you like to save the game?");
        });
        button3.setOnMouseClicked(event -> {
            ExitGame.exit("Exit", "Would you like to save the game?");
        });
        HBox top = new HBox();
        top.setStyle("-fx-background-color: #000000;");
        top.getChildren().addAll(button, button1, button2, button3);
        top.setPadding(new Insets(16, 10, 16, 10));
        top.setSpacing(15);

        main = new VBox();
        main.getChildren().add(top);
        main.setAlignment(Pos.CENTER);

        Label label = new Label("Hangman");
        label.setStyle("-fx-font: 50 arial;");
        label.setTextFill(Color.WHITE);
        main.getChildren().add(label);
        main.getChildren().get(1).setVisible(false);

        startPlay.setOnMouseClicked(event -> {
            draw();
            remainNum = 10;
            right = new VBox();
            right.setSpacing(50);
            right.setPadding(new Insets(30, 0, 0, 0));
            right.setPrefWidth(550);
            main.getChildren().get(1).setVisible(true);

            //right part of the game
            remainGuess = new Label("Remaining Guesses: 10");
            remainGuess.setStyle("-fx-font: 22 arial;");
            right.getChildren().add(remainGuess);
            startPlay.setDisable(true);
            borderPane.setRight(right);

            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(10);
            flowPane.setVgap(10);

            word = randomWord();
            for (int i = 0; i < word.length(); i++) {
                Label word1 = new Label();
                word1.setPrefSize(32, 30);
                word1.setText(Character.toString(word.toUpperCase().charAt(i)));
                word1.setTextFill(Color.WHITE);
                word1.setStyle("-fx-font: 22 arial;");
                word1.setAlignment(Pos.CENTER);
                word1.setVisible(false);
                TargetLetter stackPane = new TargetLetter(word1);
                stackPane.setStyle("-fx-background-color: black;");
                stackPane.getChildren().add(word1);
                stackPane.setPrefSize(32, 30);
                arrayList.add(stackPane);
                flowPane.getChildren().add(stackPane);
            }
            right.getChildren().add(flowPane);
            right.getChildren().add(generateAlphabet());
        });

        borderPane.setTop(main);
        borderPane.setStyle("-fx-background-color: #808080;");
        Scene scene = new Scene(borderPane, 1300, 730);
        scene.setOnKeyPressed(event -> {
            boolean b = true;
            button2.setDisable(false);
            String temp = event.getCode().toString().toUpperCase();
            int count = 0;
            int tem = 0;
            for (Button button4 : alphabet) {
                if (!button4.isDisable() && button4.getText().equals(temp)) {
                    //vocab.add(button4.getText());
                    button4.setDisable(true);
                    tem++;
                    break;
                }
            }
            if (tem != 0) {
                for (TargetLetter targetLetter : arrayList) {
                    if (targetLetter.getLabel().getText().equals(temp)) {
                        targetLetter.getLabel().setVisible(true);
                        count++;
                    }
                }
                if (count == 0 && remainNum > 0 && index < hangManBody.size()) {
                    hangManBody.get(index++).setVisible(true);
                    remainGuess.setText("Remaining Guesses: " + --remainNum);
                    if (remainNum == 0) {
                        b = false;
                        for (Button abc : alphabet) {
                            abc.setDisable(true);
                        }
                        WinOrLost.gameOver("You lost (the word was \"" + word + "\")");
                        for (TargetLetter targetLetter : arrayList) {
                            if (!targetLetter.getLabel().isVisible()) {
                                targetLetter.getLabel().setVisible(true);
                                targetLetter.setStyle("-fx-background-color: #a9a9a9");
                            }
                        }
                    }
                }
                if (b) {
                    int tempCount = 0;
                    for (TargetLetter t : arrayList) {
                        if (t.getLabel().isVisible())
                            tempCount++;
                    }
                    if (tempCount == arrayList.size()) {
                        WinOrLost.gameOver("You won!");
                        for (Button abc : alphabet) {
                            abc.setDisable(true);
                        }
                        b = false;
                    }
                }
            }
        });


        primaryStage.setScene(scene);
        primaryStage.setTitle("Hangman");
        primaryStage.show();
    }

    public String randomWord() {
        int count = 0;
        Scanner scanner;
        try {
            //URL filePath = getClass().getResource("words.txt");
            File file = new File("src/words.txt"/*filePath.toURI()*/);
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }
            System.out.println(count);
            count = (int) (Math.random() * (count)) + 1;
            scanner = new Scanner(file);
            while (count > 0) {
                word = scanner.nextLine();
                count--;
            }
            System.out.println(word);
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return word;
    }

    public static FlowPane generateAlphabet() {
        FlowPane flowPane = new FlowPane();
        for (char c = 'A'; c <= 'Z'; c++) {
            Button label = new Button(Character.toString(c));
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-background-color: #32cd32;-fx-font: 18 arial;");
            label.setPrefSize(46, 44);
            label.setAlignment(Pos.CENTER);
            flowPane.getChildren().add(label);
            alphabet.add(label);
        }
        flowPane.setHgap(8);
        flowPane.setVgap(8);
        flowPane.setMaxWidth(400);
        return flowPane;
    }

    public static void draw() {
        Pane left = new Pane();
        Line line = new Line(430, 110, 430, 40); // right vertical
        line.setStrokeWidth(3);
        line.setVisible(false);
        Line line1 = new Line(430, 40, 110, 40); // top
        line1.setStrokeWidth(3);
        line1.setVisible(false);
        Line vertical = new Line(110, 40, 110, 500);
        vertical.setVisible(false);
        vertical.setStrokeWidth(3);
        Line bottomLine = new Line(110, 500, 600, 500);
        bottomLine.setStrokeWidth(3);
        bottomLine.setVisible(false);
        Circle circle = new Circle(430, 150, 40);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3.2);
        circle.setFill(Color.GRAY);
        circle.setVisible(false);
        Line body = new Line(430, 190, 430, 320);
        body.setStrokeWidth(3);
        body.setVisible(false);
        Line leftLeg = new Line(430, 320, 320, 420);
        leftLeg.setVisible(false);
        leftLeg.setStrokeWidth(3);
        Line rightLeg = new Line(430, 320, 540, 420);
        rightLeg.setVisible(false);
        rightLeg.setStrokeWidth(3);
        Line leftArm = new Line(430, 200, 330, 180);
        leftArm.setVisible(false);
        leftArm.setStrokeWidth(3);
        Line rightArm = new Line(430, 200, 530, 180);
        rightArm.setVisible(false);
        rightArm.setStrokeWidth(3);
        left.getChildren().addAll(bottomLine, vertical, line1, line,
                circle, body, leftArm, rightArm, leftLeg, rightLeg);
        hangManBody.add(bottomLine);
        hangManBody.add(vertical);
        hangManBody.add(line1);
        hangManBody.add(line);
        hangManBody.add(circle);
        hangManBody.add(body);
        hangManBody.add(leftArm);
        hangManBody.add(rightArm);
        hangManBody.add(leftLeg);
        hangManBody.add(rightLeg);
        borderPane.setLeft(left);
    }

    public static void saveGame(File file) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(word);
            for (TargetLetter targetLetter : arrayList) {
                if (targetLetter.getLabel().isVisible()) {
                    printWriter.print(targetLetter.getLabel().getText() + "@");
                }
            }
            printWriter.println();
            for (Button button : alphabet) {
                if (button.isDisable())
                    printWriter.print(button.getText() + "@");
            }
            printWriter.println();
            printWriter.println(index);
            printWriter.println(remainNum);

            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("hng files", "*.hng");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file.getAbsolutePath().endsWith(".hng")) {
            saveGame(file);
        }
    }

    public static void load(File file) {
        borderPane.setLeft(null);
        borderPane.setRight(null);
        try {
            arrayList = new ArrayList<>();
            alphabet = new ArrayList<>();
            hangManBody = new ArrayList<>();
            Scanner scanner = new Scanner(file);
            word = scanner.nextLine();
            String[] notFoundChar = scanner.nextLine().split("@");
            String[] disableAlpha = scanner.nextLine().split("@");
            index = Integer.parseInt(scanner.nextLine());
            remainNum = Integer.parseInt(scanner.nextLine());

            draw();
            for (int i = 0; i < index; i++) {
                hangManBody.get(i).setVisible(true);
            }
            right = new VBox();
            right.setSpacing(50);
            right.setPadding(new Insets(30, 0, 0, 0));
            right.setPrefWidth(550);
            main.getChildren().get(1).setVisible(true);

            remainGuess = new Label("Remaining Guesses: " + remainNum);
            remainGuess.setStyle("-fx-font: 22 arial;");
            right.getChildren().add(remainGuess);
            startPlay.setDisable(true);
            borderPane.setRight(right);

            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(10);
            flowPane.setVgap(10);

            for (int i = 0; i < word.length(); i++) {
                Label word1 = new Label();
                word1.setPrefSize(32, 30);
                word1.setText(Character.toString(word.toUpperCase().charAt(i)));
                word1.setTextFill(Color.WHITE);
                word1.setStyle("-fx-font: 22 arial;");
                word1.setAlignment(Pos.CENTER);
                word1.setVisible(false);
                TargetLetter stackPane = new TargetLetter(word1);
                stackPane.setStyle("-fx-background-color: black;");
                stackPane.getChildren().add(word1);
                stackPane.setPrefSize(32, 30);
                arrayList.add(stackPane);
                flowPane.getChildren().add(stackPane);
            }
            right.getChildren().add(flowPane);
            right.getChildren().add(generateAlphabet());

            for (String s : disableAlpha) {
                for (Button b : alphabet) {
                    if (b.getText().equals(s)) {
                        b.setDisable(true);
                    }
                }
            }

            for (String s : notFoundChar) {
                for (TargetLetter targetLetter : arrayList) {
                    if (targetLetter.getLabel().getText().equals(s)) {
                        targetLetter.getLabel().setVisible(true);
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
