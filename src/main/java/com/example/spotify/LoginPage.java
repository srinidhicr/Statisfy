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

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class LoginPage {
    private final Stage primaryStage;
    private Scene scene;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }

    private void createUI() {
        Image spotifyLogo = new Image("file:./images/Spotify_logo.png/");
        Image backgroundImage = new Image("file:./images/login-bg.png"); // Replace with the correct path

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
        Image image2 = new Image("file:./images/login-btn.png");
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(350);
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

                AllTimeTopTracks();
                RecentlyPlayedTracks();
                TopTracks_4w();
                TopTracks_6m();


            }

        });
    }

    private void TopTracks_4w() {
        String albumCoverFolderPath = "./images/tracks/4w/albumcovers";
        String artistCoverFolderPath = "./images/tracks/4w/artistcovers";
        deleteImages(albumCoverFolderPath, artistCoverFolderPath);

        String jsonFilePath = "4_weeks.json"; // Replace with the actual path to your JSON file

        // Read JSON data from the file
        String jsonData = readFile(jsonFilePath);

        // Parse the JSON data as an array
        JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

        // Database URL, username, and password
        String dbUrl = "jdbc:mysql://localhost:3306/spotify_data"; // Update with your database name
        String dbUsername = "root";
        String dbPassword = "appleball9";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Delete the previous data without deleting the table
            String deleteSql = "DELETE FROM top_track_4w"; // Replace with your table name
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.executeUpdate();
            }

            // Reset the auto-increment counter to 1
            String resetAutoIncrementSql = "ALTER TABLE top_track_4w AUTO_INCREMENT = 1"; // Replace with your table name
            try (PreparedStatement resetAutoIncrementStatement = connection.prepareStatement(resetAutoIncrementSql)) {
                resetAutoIncrementStatement.executeUpdate();
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject track = jsonArray.get(i).getAsJsonObject();

                // Check if 'track' is null
                if (track != null) {
                    // Extract track information as needed
                    String artistName = track.getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();
                    String trackName = track.get("name").getAsString();
                    int duration = track.get("duration_ms").getAsInt();
                    String albumCoverURL = track.getAsJsonObject("album")
                            .getAsJsonArray("images")
                            .get(0).getAsJsonObject()
                            .get("url").getAsString();
                    String artistCoverURL = track.getAsJsonArray("artists")
                            .get(0).getAsJsonObject()
                            .getAsJsonObject("external_urls")
                            .get("spotify").getAsString();
                    String releaseData = track.getAsJsonObject("album")
                            .get("release_date").getAsString();

                    // Generate unique filenames based on timestamp
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    String albumCoverFileName = "album_cover_" + timestamp + ".jpg";
                    String artistCoverFileName = "artist_cover_" + timestamp + ".jpg";

                    // Download and save the images with unique filenames
                    downloadAndSaveImage(albumCoverURL, albumCoverFolderPath + "/" + albumCoverFileName);
                    downloadAndSaveImage(artistCoverURL, artistCoverFolderPath + "/" + artistCoverFileName);

                    // Insert data into the database with the unique file paths
                    insertTrack(connection, artistName, trackName, duration, albumCoverFolderPath + "/" + albumCoverFileName, artistCoverFolderPath + "/" + artistCoverFileName, releaseData, "top_track_4w");
                } else {
                    // Handle the case when 'track' is null
                    System.err.println("Skipping entry at index " + i + " because 'track' is null.");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteImages(String albumCoverFolderPath, String artistCoverFolderPath){

        File albumCoverFolder = new File(albumCoverFolderPath);
        File[] albumCoverFiles = albumCoverFolder.listFiles();
        if (albumCoverFiles != null) {
            for (File file : albumCoverFiles) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }

        // Delete all files in the artist cover folder
        File artistCoverFolder = new File(artistCoverFolderPath);
        File[] artistCoverFiles = artistCoverFolder.listFiles();
        if (artistCoverFiles != null) {
            for (File file : artistCoverFiles) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    private void downloadAndSaveImage(String imageUrl, String savePath) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStream in = connection.getInputStream();
                 FileOutputStream out = new FileOutputStream(savePath)) {
                int bytesRead;
                byte[] buffer = new byte[4096];
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImageLocally(String imageUrl, String folderPath) {
        try {
            URL url = new URL(imageUrl);
            Path path = Path.of(folderPath, getFileNameFromUrl(imageUrl));
            Files.copy(url.openStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileNameFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    private void TopTracks_6m() {

        String albumCoverFolderPath = "./images/tracks/6m/albumcovers";
        String artistCoverFolderPath = "./images/tracks/6m/artistcovers";
        deleteImages(albumCoverFolderPath, artistCoverFolderPath);

        String jsonFilePath = "6_months.json"; // Replace with the actual path to your JSON file

        // Read JSON data from the file
        String jsonData = readFile(jsonFilePath);

        // Parse the JSON data as an array
        JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

        // Database URL, username, and password
        String dbUrl = "jdbc:mysql://localhost:3306/spotify_data"; // Update with your database name
        String dbUsername = "root";
        String dbPassword = "appleball9";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Delete the previous data without deleting the table
            String deleteSql = "DELETE FROM top_track_6m"; // Replace with your table name
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.executeUpdate();
            }

            // Reset the auto-increment counter to 1
            String resetAutoIncrementSql = "ALTER TABLE top_track_6m AUTO_INCREMENT = 1"; // Replace with your table name
            try (PreparedStatement resetAutoIncrementStatement = connection.prepareStatement(resetAutoIncrementSql)) {
                resetAutoIncrementStatement.executeUpdate();
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject track = jsonArray.get(i).getAsJsonObject();

                // Check if 'track' is null
                if (track != null) {
                    // Extract track information as needed
                    String artistName = track.getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();
                    String trackName = track.get("name").getAsString();
                    int duration = track.get("duration_ms").getAsInt();
                    String albumCoverURL = track.getAsJsonObject("album")
                            .getAsJsonArray("images")
                            .get(0).getAsJsonObject()
                            .get("url").getAsString();
                    String artistCoverURL = track.getAsJsonArray("artists")
                            .get(0).getAsJsonObject()
                            .getAsJsonObject("external_urls")
                            .get("spotify").getAsString();
                    String releaseData = track.getAsJsonObject("album")
                            .get("release_date").getAsString();

                    // Generate unique filenames based on timestamp
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    String albumCoverFileName = "album_cover_" + timestamp + ".jpg";
                    String artistCoverFileName = "artist_cover_" + timestamp + ".jpg";

                    // Download and save the images with unique filenames
                    downloadAndSaveImage(albumCoverURL, albumCoverFolderPath + "/" + albumCoverFileName);
                    downloadAndSaveImage(artistCoverURL, artistCoverFolderPath + "/" + artistCoverFileName);

                    // Insert data into the database
                    insertTrack(connection, artistName, trackName, duration, albumCoverFolderPath + "/" + albumCoverFileName, artistCoverFolderPath + "/" + artistCoverFileName, releaseData, "top_track_6m");
                } else {
                    // Handle the case when 'track' is null
                    System.err.println("Skipping entry at index " + i + " because 'track' is null.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AllTimeTopTracks() {
        String jsonFilePath = "all_time_top_tracks.json"; // Replace with the actual path to your JSON file

        // Read JSON data from the file
        String jsonData = readFile(jsonFilePath);

        // Parse the JSON data as an array
        JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

        // Database URL, username, and password
        String dbUrl = "jdbc:mysql://localhost:3306/spotify_data"; // Update with your database name
        String dbUsername = "root";
        String dbPassword = "appleball9";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Delete the previous data without deleting the table
            String deleteSql = "DELETE FROM recently_played_tracks"; // Replace with your table name
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.executeUpdate();
            }

            // Reset the auto-increment counter to 1
            String resetAutoIncrementSql = "ALTER TABLE recently_played_tracks AUTO_INCREMENT = 1"; // Replace with your table name
            try (PreparedStatement resetAutoIncrementStatement = connection.prepareStatement(resetAutoIncrementSql)) {
                resetAutoIncrementStatement.executeUpdate();
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject track = jsonArray.get(i).getAsJsonObject();

                // Check if 'track' is null
                if (track != null) {
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
                    insertTrack(connection, artistName, trackName, duration, albumCover, artistCover, releaseData, "users_top_tracks");
                } else {
                    // Handle the case when 'track' is null
                    System.err.println("Skipping entry at index " + i + " because 'track' is null.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void RecentlyPlayedTracks() {
        String jsonFilePath = "recently_played.json"; // Replace with the actual path to your JSON file

        // Read JSON data from the file
        String jsonData = readFile(jsonFilePath);

        // Parse the JSON data as an array
        JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

        // Database URL, username, and password
        String dbUrl = "jdbc:mysql://localhost:3306/spotify_data"; // Update with your database name
        String dbUsername = "root";
        String dbPassword = "appleball9";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Delete the previous data without deleting the table
            String deleteSql = "DELETE FROM recently_played_tracks"; // Replace with your table name
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.executeUpdate();
            }

            // Reset the auto-increment counter to 1
            String resetAutoIncrementSql = "ALTER TABLE recently_played_tracks AUTO_INCREMENT = 1"; // Replace with your table name
            try (PreparedStatement resetAutoIncrementStatement = connection.prepareStatement(resetAutoIncrementSql)) {
                resetAutoIncrementStatement.executeUpdate();
            }

            System.out.println("\n\n");
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject trackObject = jsonArray.get(i).getAsJsonObject();
                JsonObject track = trackObject.getAsJsonObject("track");

                // Extract track information as needed
                String artistName = track.getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();
                String trackName = track.get("name").getAsString();
                int duration = track.get("duration_ms").getAsInt();
                String albumCoverURL = track.getAsJsonObject("album")
                        .getAsJsonArray("images")
                        .get(0).getAsJsonObject()
                        .get("url").getAsString();
                String artistCoverURL = track.getAsJsonArray("artists")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("external_urls")
                        .get("spotify").getAsString();
                String releaseData = track.getAsJsonObject("album")
                        .get("release_date").getAsString();


                // Insert data into the database
                insertTrack(connection, artistName, trackName, duration, albumCoverURL, artistCoverURL, releaseData, "recently_played_tracks");
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
                                    String albumCover, String artistCover, String releaseData, String tableName) throws SQLException {
        String insertSQL = "INSERT INTO " + tableName + " (artist_name, track_name, duration, album_cover, artist_cover, release_data) " +
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
