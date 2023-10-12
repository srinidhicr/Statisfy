package com.example.spotify;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Dashboard {

    private Stage primaryStage;
    private Scene scene;

    public Dashboard(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }

    private void createUI() {
        Font font = Font.font("Gotham Rounded", 40);
        HBox topBar = createTopBar();

        // Create the scene and set it to the stage
        StackPane root = new StackPane();
        // Load your background image
        Image backgroundImage = new Image("file:./images/dashboard-bg.jpg"); // Replace with the correct path

        // Set the background image
        BackgroundImage backgroundImg = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        Background background = new Background(backgroundImg);
        root.setBackground(background);

        Image splogo= new Image("file:./images/splogo2.png");
        ImageView splogoImageView = new ImageView(splogo);
        splogoImageView.setFitWidth(150); // Adjust the size as needed
        splogoImageView.setFitHeight(150); // Adjust the size as needed
        splogoImageView.setTranslateY(-350);

        Text note = new Text("STATISFY");
        note.setFill(Color.WHITE);
        note.setFont(font);
        note.setTranslateY(-250);

        // Create a VBox to hold the button grid and note
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(20);

        // Create a GridPane for the buttons
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(20); // Horizontal gap between buttons
        buttonGrid.setVgap(20); // Vertical gap between buttons
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setTranslateY(-200);

        Button artists = createButton2("Artists");
        Button tracks = createButton2("Tracks");
        Button albums = createButton2("Albums");
        Button genres = createButton2("Genres");

        artists.setOnAction(e -> {
            Artists artist = new Artists(primaryStage);
            primaryStage.setTitle("Spotify - Artists Analysis");
            primaryStage.setScene(artist.getScene());
            primaryStage.show();
        });

        tracks.setOnAction(e -> {
            Tracks track = new Tracks(primaryStage);
            primaryStage.setTitle("Spotify - Tracks Analysis");
            primaryStage.setScene(track.getScene());
            primaryStage.show();
        });

        albums.setOnAction(e -> {
            Albums album = new Albums(primaryStage);
            primaryStage.setTitle("Spotify - Album Analysis");
            primaryStage.setScene(album.getScene());
            primaryStage.show();
        });

        albums.setOnAction(e -> {
            Genres genre = new Genres(primaryStage);
            primaryStage.setTitle("Spotify - Genre Analysis");
            primaryStage.setScene(genre.getScene());
            primaryStage.show();
        });

        // Add buttons to the GridPane at specific row and column positions
        buttonGrid.add(artists, 0, 0);
        buttonGrid.add(tracks, 1, 0);
        buttonGrid.add(albums, 0, 1);
        buttonGrid.add(genres, 1, 1);

        content.getChildren().addAll(splogoImageView, note, buttonGrid, topBar);

        scene = new Scene(root, 1200, 1250);

        // Add content first, then borderPane
        root.getChildren().addAll(content);
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

        topBar.setTranslateY(-770);
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

    private Button createButton2(String text) {
        Button button = new Button(text);

        button.setPrefWidth(200);
        button.setPrefHeight(40);
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color:  RGB(1,76,66); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 18px;");
        });

        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: RGB(30, 223, 99); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 18px;");
        });

        button.setStyle("-fx-background-color:  RGB(30, 223, 99); -fx-text-fill: white; -fx-font-family: 'Gotham Rounded'; -fx-font-size: 18px;");
        button.setOnAction(e -> System.out.println(text + " clicked"));
        return button;
    }

    public Scene getScene() {
        return scene;
    }
}