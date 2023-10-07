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
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SignupPage {
    private Stage primaryStage;
    private Scene scene;

    public SignupPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }

    private void createUI() {
        Image image1 = new Image("file:/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/main/java/com/example/spotify/Spotify_logo.png");

        Font font = Font.font("Gotham Rounded", 16);
        Font font1 = Font.font("Gotham Rounded Bold", 25);

        Text cap = new Text("Sign up for free to start listening.");
        cap.setFill(Color.rgb(27, 215, 95, 1.0));

        cap.setFont(font1);
        cap.setTextAlignment(TextAlignment.CENTER);
        cap.setTranslateY(-300);

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image1);
        imageView.setFitWidth(320);
        imageView.setFitHeight(100);
        imageView.setTranslateY(-370);

        Text fname = new Text("Your fullname?");
        fname.setFill(Color.WHITE);
        fname.setFont(font);
        fname.setTranslateY(20);
        TextField fullName = new TextField();
        fullName.setPromptText("Full Name");
        fullName.setPrefWidth(500);
        fullName.setPrefHeight(40);
        fullName.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

        Text uname = new Text("What should we call you?");
        uname.setFill(Color.WHITE);
        uname.setFont(font);
        uname.setTranslateY(20);
        TextField userName = new TextField();
        userName.setPromptText("User Name");
        userName.setPrefWidth(500);
        userName.setPrefHeight(40);
        userName.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

        Text p1 = new Text("Create a password");
        p1.setFill(Color.WHITE);
        p1.setFont(font);
        p1.setTranslateY(20);
        PasswordField pass1 = new PasswordField();
        pass1.setPromptText("Password");
        pass1.setPrefWidth(500);
        pass1.setPrefHeight(40);
        pass1.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

        Text p2 = new Text("Cross checking your password");
        p2.setFill(Color.WHITE);
        p2.setFont(font);
        p2.setTranslateY(20);
        PasswordField pass2 = new PasswordField();
        pass2.setPromptText("Re-enter Password");
        pass2.setPrefWidth(500);
        pass2.setPrefHeight(40);
        pass2.setStyle("-fx-background-color: RGB(51, 51, 51); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16;");

        GridPane root = new GridPane();
        root.addRow(0, fname);
        root.addRow(1, fullName);
        root.addRow(2, uname);
        root.addRow(3, userName);
        root.addRow(4, p1);
        root.addRow(5, pass1);
        root.addRow(6, p2);
        root.addRow(7, pass2);

        root.setAlignment(Pos.CENTER);
        // Set vertical gap (space) between rows in the GridPane
        root.setVgap(30);
        root.setTranslateY(-50);

        Rectangle rectangle = new Rectangle(700, 810); // Adjust the size as needed
        rectangle.setStyle("-fx-background-color: RGB(51, 51, 51);"); // Set the color of the rectangle
        rectangle.setTranslateY(-80);

        StackPane stackPane = new StackPane();

        // Add the black rectangle to the StackPane
        stackPane.getChildren().add(rectangle);
        stackPane.getChildren().add(root);

        Button signin = new Button();
        Image image2 = new Image("file:/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/main/java/com/example/spotify/sign-up-2.png");
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(135);
        imageView2.setFitHeight(50);
        signin.setStyle("-fx-background-color: RGB(0, 0, 0);");
        signin.setTranslateY(235);
        signin.setGraphic(imageView2);

        Button login = new Button("Have an account? Log in.");
        login.setStyle("-fx-background-color: RGB(0, 0, 0);-fx-text-fill: white;-fx-font-size: 16px; -fx-font-family: 'Gotham Rounded';-fx-underline: true;");
        login.setTranslateY(280);

        StackPane redirectedLayout = new StackPane();
        Scene redirectedScene = new Scene(redirectedLayout, 1200, 1250);
        primaryStage.setTitle("Spotify");
        redirectedScene.getRoot().setStyle("-fx-background-color: RGB(30, 30, 30);");
        redirectedLayout.getChildren().addAll(stackPane, imageView, signin, cap, login);

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LoginPage loginPage = new LoginPage(primaryStage);
                primaryStage.setTitle("Spotify - Login");
                primaryStage.setScene(loginPage.getScene());
            }
        });

        signin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StackPane redirectedLayout = new StackPane();
                Scene redirectedScene = new Scene(redirectedLayout, 1200, 1250);
                primaryStage.setTitle("Spotify");
                redirectedScene.getRoot().setStyle("-fx-background-color: RGB(30, 30, 30);");

                primaryStage.setScene(redirectedScene);
            }
        });
        // Initialize the scene
        scene = redirectedScene;
    }

    public Scene getScene() {
        return scene;
    }
}
