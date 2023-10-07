package com.example.spotify;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Spotify - Login");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Font.loadFont(getClass().getResourceAsStream("/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/main/java/com/example/spotify/gotham-rounded/GothamRoundedBook_21018.ttf"), 12);

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

        // Create the scene
        Scene scene = new Scene(root, 1200, 1250);

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
                stage.setTitle("Spotify");
                redirectedScene.getRoot().setStyle("-fx-background-color: RGB(30, 30, 30);");



                stage.setScene(redirectedScene);
            }
        });

        //SIGNUP
        Button signup = new Button("New to Spotify? Sign up here.");
        signup.setStyle("-fx-background-color: RGB(0, 0, 0);-fx-text-fill: white;-fx-font-size: 16px; -fx-font-family: 'Gotham Rounded';-fx-underline: true;");
        signup.setTranslateY(210);
        signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Image image1 = new Image("file:/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/main/java/com/example/spotify/Spotify_logo.png");

                // Create an ImageView to display the image
                ImageView imageView = new ImageView(image1);
                imageView.setFitWidth(320);
                imageView.setFitHeight(100);
                imageView.setTranslateY(-250);

                TextField fullName = new TextField();
                fullName.setPromptText("Full Name");
                fullName.setPrefWidth(500);
                fullName.setPrefHeight(40);
                fullName.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

                TextField userName = new TextField();
                userName.setPromptText("User Name");
                userName.setPrefWidth(500);
                userName.setPrefHeight(40);
                userName.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

                PasswordField pass1 = new PasswordField();
                pass1.setPromptText("Password");
                pass1.setPrefWidth(500);
                pass1.setPrefHeight(40);
                pass1.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

                PasswordField pass2 = new PasswordField();
                pass2.setPromptText("Re-enter Password");
                pass2.setPrefWidth(500);
                pass2.setPrefHeight(40);
                pass2.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

                GridPane root = new GridPane();
                root.addRow(0, fullName);
                root.addRow(1, userName);
                root.addRow(2, pass1);
                root.addRow(3, pass2);

                root.setAlignment(Pos.CENTER);

                Rectangle rectangle = new Rectangle(700, 700); // Adjust the size as needed
                rectangle.setStyle("-fx-background-color: RGB(51, 51, 51);"); // Set the color of the rectangle
                rectangle.setTranslateY(-40);

                // Set vertical gap (space) between rows in the GridPane
                root.setVgap(30);

                StackPane redirectedLayout = new StackPane();
                Scene redirectedScene = new Scene(redirectedLayout, 1200, 1250);
                stage.setTitle("Spotify");
                redirectedScene.getRoot().setStyle("-fx-background-color: RGB(30, 30, 30);");
                redirectedLayout.getChildren().addAll(rectangle, imageView, root);
                stage.setScene(redirectedScene);
                stage.show();
            }
        });

        root.getChildren().addAll(rectangle, imageView, root1, loginButton, signup);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}