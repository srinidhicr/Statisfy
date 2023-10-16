package com.example.spotify;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;

public class Tracks {
    private Stage primaryStage;
    private Scene scene;
    private ObservableList<TrackData> data = FXCollections.observableArrayList();

    private VBox content;

    private TableView<TrackData> tableView;  // Only one TableView

    public Tracks(Stage primaryStage) {
        this.primaryStage = primaryStage;
        tableView = new TableView<>();  // Initialize the single TableView
        createUI();
    }

    private void createUI() {
        HBox topBar = createTopBar();
        Font font = Font.font("Gotham Rounded", 40);

        StackPane root = new StackPane();
        scene = new Scene(root, 1200, 1250); // Assign the scene to the instance variable

        content = new VBox();
        content.getChildren().addAll(topBar);
        content.setAlignment(Pos.TOP_RIGHT);

        Image splogo = new Image("file:./images/splogo2.png");
        ImageView splogoImageView = new ImageView(splogo);
        splogoImageView.setFitWidth(150);
        splogoImageView.setFitHeight(150);
        splogoImageView.setTranslateY(-350);

        Text note = new Text("TOP TRACKS");
        note.setFill(Color.WHITE);
        note.setFont(font);
        note.setTranslateY(-250);

        HBox topTracksButtons = createTopTracksButtons();
        topTracksButtons.setAlignment(Pos.CENTER);
        topTracksButtons.setTranslateY(400);
        content.getChildren().add(topTracksButtons);

        // Set the background color of the scene to black
        scene.getRoot().setStyle("-fx-background-color: black;");
        root.getChildren().addAll(content, note, splogoImageView);
        primaryStage.setScene(scene); // Set the scene for primaryStage
        primaryStage.show();
    }

    private HBox createTopTracksButtons() {
        HBox topTracksButtons = new HBox();
        topTracksButtons.setSpacing(20); // Adjust the spacing as needed

        Button top4w = createButton2("4 weeks");
        Button top6m = createButton2("6 months");
        Button topt = createButton2("All time");
        Button recent = createButton2("Recently played");

        top4w.setOnAction(e -> {
            fetchAndDisplayTopTracks("top_track_4w", tableView);
        });

        top6m.setOnAction(e -> {
            fetchAndDisplayTopTracks("top_track_6m", tableView);
        });

        topt.setOnAction(e -> {
            fetchAndDisplayTopTracks("users_top_tracks", tableView);
        });

        // Add all buttons to the same row (HBox)
        topTracksButtons.getChildren().addAll(top4w, top6m, topt, recent);

        return topTracksButtons;
    }

    private void fetchAndDisplayTopTracks(String tableName, TableView<TrackData> tableView) {
        // Clear the previous data
        tableView.getItems().clear();
        tableView.getColumns().clear();
        data.clear();

        String dbUrl = "jdbc:mysql://localhost:3306/spotify_data";
        String dbUsername = "root";
        String dbPassword = "appleball9";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM " + tableName;

            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        String artistName = resultSet.getString("artist_name");
                        String trackName = resultSet.getString("track_name");
                        int duration = resultSet.getInt("duration");
                        String albumCoverLocalPath = resultSet.getString("album_cover");
                        String artistCoverLocalPath = resultSet.getString("artist_cover");
                        String releaseDate = resultSet.getString("release_data");

                        // Create a TrackData instance and add it to the data list
                        TrackData track = new TrackData(artistName, trackName, duration, albumCoverLocalPath, artistCoverLocalPath, releaseDate);
                        data.add(track);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create columns and configure the table view
        TableColumn<TrackData, String> artistNameCol = new TableColumn<>("Artist Name");
        artistNameCol.setCellValueFactory(new PropertyValueFactory<>("artistName"));

        TableColumn<TrackData, String> trackNameCol = new TableColumn<>("Track Name");
        trackNameCol.setCellValueFactory(new PropertyValueFactory<>("trackName"));

        TableColumn<TrackData, String> releaseDateCol = new TableColumn<>("Release Date");
        releaseDateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        TableColumn<TrackData, Image> albumImageCol = new TableColumn<>("Album Image");
        albumImageCol.setCellValueFactory(param -> {
            // Here, we return the Image object from the local file path
            Image albumImage = new Image("file:" + param.getValue().getAlbumURL());
            return new SimpleObjectProperty<>(albumImage);
        });

        albumImageCol.setCellFactory(column -> {
            return new TableCell<TrackData, Image>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        imageView.setImage(item);
                        imageView.setFitWidth(50); // Set the desired width
                        imageView.setFitHeight(50); // Set the desired height
                        setGraphic(imageView);
                    }
                }
            };
        });

        tableView.setStyle("-fx-cell-size: 100px; -fx-padding: 10px; -fx-background-color: lightgray;");
        tableView.setTranslateY(400);
        tableView.getColumns().addAll(albumImageCol, artistNameCol, trackNameCol, releaseDateCol);
        tableView.setItems(data);
        content.getChildren().add(tableView);
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
        return button;
    }

    public Scene getScene() {
        return scene;
    }
}
