CREATE DATABASE spotify-data;
USE spotify-data;

CREATE TABLE IF NOT EXISTS recently_played_tracks (
     id INT AUTO_INCREMENT PRIMARY KEY,
     artist_name VARCHAR(255),
     track_name VARCHAR(255),
     duration INT,
     album_cover VARCHAR(255),
     artist_cover VARCHAR(255),
     release_data VARCHAR(10)
 );

DROP TABLE recently_played_tracks;