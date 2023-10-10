package com.example.spotify;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SpotifyApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the scene
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1200, 1250);

        // Set the background color of the scene to black
        scene.getRoot().setStyle("-fx-background-color: black;");
        LoginPage loginPage = new LoginPage(primaryStage);
        primaryStage.setTitle("Spotify - Login");
        primaryStage.setScene(loginPage.getScene());
        primaryStage.show();
    }
}
