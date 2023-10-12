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

CREATE TABLE IF NOT EXISTS users_top_tracks (
     id INT AUTO_INCREMENT PRIMARY KEY,
     artist_name VARCHAR(255),
     track_name VARCHAR(255),
     duration INT,
     album_cover VARCHAR(255),
     artist_cover VARCHAR(255),
     release_data VARCHAR(10)
 );


 DELETE FROM recently_played_tracks;

 ALTER TABLE recently_played_tracks AUTO_INCREMENT = 1;

DROP TABLE recently_played_tracks;

CREATE TABLE IF NOT EXISTS top_track_4w (
     id INT AUTO_INCREMENT PRIMARY KEY,
     artist_name VARCHAR(255),
     track_name VARCHAR(255),
     duration INT,
     album_cover VARCHAR(255),
     artist_cover VARCHAR(255),
     release_data VARCHAR(10)
 );

 CREATE TABLE IF NOT EXISTS top_track_6m (
      id INT AUTO_INCREMENT PRIMARY KEY,
      artist_name VARCHAR(255),
      track_name VARCHAR(255),
      duration INT,
      album_cover VARCHAR(255),
      artist_cover VARCHAR(255),
      release_data VARCHAR(10)
  );