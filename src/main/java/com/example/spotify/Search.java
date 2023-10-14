

package com.example.spotify;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Search {
    private Stage primaryStage;
    private Scene scene;

    public Search(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }

    private void createUI(){
        HBox topBar = createTopBar();
        topBar.setTranslateY(-720);
        Font font = Font.font("Gotham Rounded", 50);
        StackPane root = new StackPane();
        scene = new Scene(root, 1200, 1250);

        Image splogo = new Image("file:./images/splogo2.png");
        ImageView splogoImageView = new ImageView(splogo);
        splogoImageView.setFitWidth(100);
        splogoImageView.setFitHeight(100);
        splogoImageView.setTranslateY(-250);
        splogoImageView.setTranslateX(-130);

        Text note = new Text("SEARCH");
        note.setFill(Color.WHITE);
        note.setFont(font);
        note.setTranslateX(50);
        note.setTranslateY(-400);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setPrefWidth(500);
        searchBar.setPrefHeight(40);
        searchBar.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 16; -fx-background-radius: 20px");
        searchBar.setTranslateY(-170);

        GridPane grid = new GridPane();
        grid.addRow(0, searchBar);
        grid.setAlignment(Pos.CENTER);
        grid.setTranslateY(-50);
        // Set the background color of the scene to black

        // Create a VBox to hold the button grid and note
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);

        content.getChildren().addAll(splogoImageView, grid, note, topBar);

        root.getChildren().addAll(content);
        scene.getRoot().setStyle("-fx-background-color: black;");
        primaryStage.setScene(scene); // Set the scene for primaryStage
        primaryStage.show();
    }
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10, 20, 10, 20)); // Adjust the padding as needed
        Button home = createButton("Home");
        Button search = createButton("Search");

        home.setOnAction(e -> {
            Dashboard dashboard = new Dashboard(primaryStage);
            primaryStage.setTitle("Spotify - Dashboard");
            primaryStage.setScene(dashboard.getScene());
            primaryStage.show();
        });

        search.setOnAction(e -> {
            Search search1 = new Search(primaryStage);
            primaryStage.setTitle("Spotify - Search");
            primaryStage.setScene(search1.getScene());
            primaryStage.show();
        });

        // Create a spacer to push the "Account" button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Create a MenuButton for the "Account" dropdown
        MenuButton accountMenu = new MenuButton("Account");
        accountMenu.setStyle("-fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-background-color: RGB(30, 223, 99); -fx-font-size: 18px; ");
        accountMenu.setPrefWidth(200);
        accountMenu.setPrefHeight(40);

        // Create a MenuItem for the "Log Out" option
        MenuItem logoutItem = new MenuItem("Log Out");
        logoutItem.setOnAction(e -> {
            // Handle the log out action here
            System.out.println("Logged out");
        });

        // Add the Menu to the MenuButton
        accountMenu.getItems().add(logoutItem);

        topBar.getChildren().addAll(home, search, spacer, accountMenu);

        //topBar.setTranslateY(-770);
        topBar.setSpacing(15);
        topBar.setStyle("-fx-background-color: RGB(51, 51, 51);"); // Set background color for the top bar
        return topBar;
    }

    private Button createButton(String text) {
        Button button = new Button(text);

        button.setPrefWidth(200);
        button.setPrefHeight(40);
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 18px;");
        });

        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 18px;");
        });

        button.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 18px;");

        return button;
    }

    public Scene getScene() {
        return scene;
    }

}
