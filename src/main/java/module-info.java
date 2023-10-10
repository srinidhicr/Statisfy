module com.example.spotify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;


    opens com.example.spotify to javafx.fxml;
    exports com.example.spotify;
}