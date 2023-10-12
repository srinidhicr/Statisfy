package com.example.spotify;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Artists {
    private Stage primaryStage;
    private Scene scene;

    public Artists(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }

    private void createUI() {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1200, 1250);

        // Set the background color of the scene to black
        scene.getRoot().setStyle("-fx-background-color: black;");
        primaryStage.show();
    }


    public Scene getScene() {
        return scene;
    }
}
