package com.example.triviagame;

import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
//import javafx.scene.media.Media;


public class HelloApplication extends Application {

    private final String[] questions = {
            "Lesotho is entirely surrounded by which country?",
            "What is the highest pont in Lesotho?",
            "Lesotho is known for its large population of what kind of animal?",
            "WHat is the capital of Lesotho?"
    };

    private final String[][] options = {
            {"South Africa", "Botswana", "Malawi", "Namibia"},
            {"Mount Everest", "Thaba Bosiu", "Maluti Mountain", "Sani Pass"},
            {"Crocodiles", "Horses", "Sheep", "Elephants"},
            {"Mafeteng", "Leribe", "Maseru", "Pretoria"}
    };

    private final String[] answers = {"South Africa", "Thaba Bosiu", "Sheep", "Maseru"};

    private int index = 0;
    private int correctGuesses = 0;

    private Label questionLabel;
    private RadioButton[] optionButtons;
    private ToggleGroup toggleGroup;
    private Label timerLabel;
    private Timeline timeline;
    private ImageView imageView;
   // private int correct = 0;


    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));

        questionLabel = new Label();
        questionLabel.getStyleClass().add("question-label");
        vBox.getChildren().add(questionLabel);
        imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);


        vBox.getChildren().add(imageView);

        optionButtons = new RadioButton[4];
        toggleGroup = new ToggleGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new RadioButton();
            optionButtons[i].getStyleClass().add("option-button");
            optionButtons[i].setToggleGroup(toggleGroup);
            vBox.getChildren().add(optionButtons[i]);
        }

        timerLabel = new Label();
        timerLabel.getStyleClass().add("timer-label");
        vBox.getChildren().add(timerLabel);

        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(e -> {
            checkAnswer();

            index++;
            if (index < questions.length) {
                loadQuestion();
            } //else {
                // Display result
               // System.out.println("Quiz finished. Correct answers: " + correctGuesses);
           // }
        });
        vBox.getChildren().add(submitButton);

        borderPane.setCenter(vBox);

        loadQuestion();

        Scene scene = new Scene(borderPane, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trivia Game");
        primaryStage.show();

        // Timer setup
        final int[] duration = {20};
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timerLabel.setText("To view your score wait for the time ");
            timerLabel.setText("Time Left: " + duration[0]);
            duration[0]--;

            if (duration[0] <= 0) {
                timeline.stop();
                timerLabel.setText("Time's up");
                System.out.println("Time's up!");
                nextScene();
                System.out.println("Quiz finished. Correct answers: " + correctGuesses);
                System.out.println("you answered "+index+" questions out of 4");

            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void nextScene() {
        VBox sceneLayout = new VBox();
        sceneLayout.setAlignment(Pos.CENTER);
        sceneLayout.setSpacing(20);
        Label results = new Label("Here are your results");

        Label Qnumbers = new Label("You answered "+index+" questions out of 4");

        Label guesses = new Label("correct answers = "+correctGuesses);

        sceneLayout.getChildren().addAll(results,Qnumbers,guesses);
        Scene nxtScene = new Scene(sceneLayout,600,400);
        Stage stage = (Stage) questionLabel.getScene().getWindow();
        stage.setScene(nxtScene);

    }

    private void loadQuestion() {
        questionLabel.setText(questions[index]);

        String[] imagesP = {
                "map.jpg",
                "t.jpg",
                "3.png",
                "city.jpg"
        };
        //loading an image based on the index provided
        String imageP = imagesP[index];
        Image image = new Image(getClass().getResourceAsStream(imageP));
        imageView.setImage(image);

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[index][i]);
        }
        toggleGroup.selectToggle(null); //to Clear selection
    }

    private void checkAnswer() {
        RadioButton selectedButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedButton != null) {
            String guess = selectedButton.getText();
            if (guess == answers[index]) {
                correctGuesses++;


            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
