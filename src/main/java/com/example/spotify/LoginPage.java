package com.example.spotify;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;

public class LoginPage {
    private final Stage primaryStage;
    private Scene scene;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }

    private void createUI() {
        Image spotifyLogo = new Image("file:/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/main/java/com/example/spotify/Spotify_logo.png");
        Image backgroundImage = new Image("file:/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/main/java/com/example/spotify/login-bg.png"); // Replace with the correct path

        Font font = Font.font("Gotham Rounded", 16);

        // Spotify logo
        ImageView spotifylogo = new ImageView(spotifyLogo);
        spotifylogo.setFitWidth(320);
        spotifylogo.setFitHeight(100);
        spotifylogo.setTranslateY(-150);

        // black background rectangle
        Rectangle rectangle = new Rectangle(400, 425);
        rectangle.setArcWidth(20); // Adjust the arc width as needed
        rectangle.setArcHeight(20); // Adjust the arc height as needed
        rectangle.setFill(Color.rgb(0, 0, 0));
        rectangle.setTranslateY(-50);


        // Set the background image
        BackgroundImage backgroundImg = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        Background background = new Background(backgroundImg);

        // login button
        Button loginButton = new Button();
        Image image2 = new Image("file:/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/spotify-1/src/main/java/com/example/spotify1/login-btn.png");
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(200);
        imageView2.setFitHeight(50);
        imageView2.setTranslateY(0);
        loginButton.setStyle("-fx-background-color: RGB(0, 0, 0);");
        loginButton.setTranslateY(-10);
        loginButton.setGraphic(imageView2);

        Text note = new Text("Verified by Spotify Oauth2.0");
        note.setFill(Color.WHITE);
        note.setFont(font);
        note.setTranslateY(45);

        StackPane root1 = new StackPane();
        // Set the background for root1
        root1.setBackground(background);

        scene = new Scene(root1, 1200, 1250);
        root1.getChildren().addAll(rectangle, spotifylogo, loginButton, note);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /*
                Dashboard dashboard = new Dashboard(primaryStage);
                primaryStage.setTitle("Spotify - Dashboard");
                primaryStage.setScene(dashboard.getScene());
                primaryStage.show();
                */

                try {
                    // Execute the Spotify authorization Python script
                    ProcessBuilder processBuilder = new ProcessBuilder("python3", "src/python-scripts/main.py");
                    Process process = processBuilder.start();
                    int exitCode = process.waitFor();

                    // Check if the Python script was executed successfully
                    if (exitCode == 0) {
                        Dashboard dashboard = new Dashboard(primaryStage);
                        primaryStage.setTitle("Spotify - Dashboard");
                        primaryStage.setScene(dashboard.getScene());
                        primaryStage.show();
                    } else {
                        // Handle any error or failure
                        System.out.println("Spotify authorization script failed.");
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                RecentlyPlayedTracks();

            }

        });

    }

    private void RecentlyPlayedTracks() {
        String jsonFilePath = "/Users/srinidhicr/Documents/Mine/vscode/sem5-packages/Spotify/src/python-scripts/recently_played.json"; // Replace with the actual path to your JSON file

        // Read JSON data from the file
        String jsonData = readFile(jsonFilePath);

        // Parse the JSON data as an array
        JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

        // Database URL, username, and password
        String dbUrl = "jdbc:mysql://localhost:3306/spotify_data"; // Update with your database name
        String dbUsername = "root";
        String dbPassword = "appleball9";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject trackObject = jsonArray.get(i).getAsJsonObject();
                JsonObject track = trackObject.getAsJsonObject("track");

                // Extract track information as needed
                String artistName = track.getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();
                String trackName = track.get("name").getAsString();
                int duration = track.get("duration_ms").getAsInt();
                String albumCover = track.getAsJsonObject("album")
                        .getAsJsonArray("images")
                        .get(0).getAsJsonObject()
                        .get("url").getAsString();
                String artistCover = track.getAsJsonArray("artists")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("external_urls")
                        .get("spotify").getAsString();
                String releaseData = track.getAsJsonObject("album")
                        .get("release_date").getAsString();

                // Insert data into the database
                insertTrack(connection, artistName, trackName, duration, albumCover, artistCover, releaseData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    private String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    private static void insertTrack(Connection connection, String artistName, String trackName, int duration,
                                    String albumCover, String artistCover, String releaseData) throws SQLException {
        String insertSQL = "INSERT INTO recently_played_tracks (artist_name, track_name, duration, album_cover, artist_cover, release_data) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, artistName);
            preparedStatement.setString(2, trackName);
            preparedStatement.setInt(3, duration);
            preparedStatement.setString(4, albumCover);
            preparedStatement.setString(5, artistCover);
            preparedStatement.setString(6, releaseData);

            preparedStatement.executeUpdate();
        }
    }


    public Scene getScene() {
        return scene;
    }
}
