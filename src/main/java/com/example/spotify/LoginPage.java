package com.example.spotify;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginPage {
    private Stage primaryStage;
    private Scene scene;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }

    private void createUI() {
        Image image1 = new Image("file:/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/main/java/com/example/spotify/Spotify_logo.png");

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image1);
        imageView.setFitWidth(320);
        imageView.setFitHeight(100);
        imageView.setTranslateY(-150);

        // Create textfields
        TextField username = new TextField();
        username.setPromptText("Username");
        username.setPrefWidth(500);
        username.setPrefHeight(40);
        username.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(500);
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

        GridPane root1 = new GridPane();
        root1.addRow(0, username);
        root1.addRow(1, passwordField);

        root1.setAlignment(Pos.CENTER);

        // Set vertical gap (space) between rows in the GridPane
        root1.setVgap(30); // Adjust the gap as needed

        Rectangle rectangle = new Rectangle(700, 700); // Adjust the size as needed
        rectangle.setStyle("-fx-background-color: RGB(51, 51, 51);"); // Set the color of the rectangle

        // Create a StackPane
        StackPane root = new StackPane();

        // Initialize the scene
        scene = new Scene(root, 1200, 1250);

        // Set the background color of the scene to black
        scene.getRoot().setStyle("-fx-background-color: RGB(20, 20, 20);");

        // Create a Button for login
        Button loginButton = new Button();
        Image image2 = new Image("file:/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/main/java/com/example/spotify/login-btn.png");
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(320);
        imageView2.setFitHeight(50);
        loginButton.setStyle("-fx-background-color: RGB(0, 0, 0);");
        loginButton.setTranslateY(150);
        loginButton.setGraphic(imageView2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StackPane redirectedLayout = new StackPane();
                Scene redirectedScene = new Scene(redirectedLayout, 1200, 1250);
                primaryStage.setTitle("Spotify");
                redirectedScene.getRoot().setStyle("-fx-background-color: RGB(30, 30, 30);");

                primaryStage.setScene(redirectedScene);
            }
        });

        //SIGNUP
        Button signup = new Button("New to Spotify? Sign up here.");
        signup.setStyle("-fx-background-color: RGB(0, 0, 0);-fx-text-fill: white;-fx-font-size: 16px; -fx-font-family: 'Gotham Rounded';-fx-underline: true;");
        signup.setTranslateY(210);

        root.getChildren().addAll(rectangle, imageView, root1, loginButton, signup);

        signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Create an instance of SignupPage
                SignupPage signupPage = new SignupPage(primaryStage);

                // Set the scene to the signup page
                primaryStage.setScene(signupPage.getScene());
            }
        });
    }

    public Scene getScene() {
        return scene;
    }
}
