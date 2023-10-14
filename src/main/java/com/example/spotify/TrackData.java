package com.example.spotify;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TrackData {
    private final SimpleStringProperty artistName;
    private final SimpleStringProperty trackName;
    private final SimpleIntegerProperty duration;
    private final SimpleStringProperty albumURL;
    private final SimpleStringProperty artistURL;
    private final SimpleStringProperty releaseDate;

    public TrackData(String artistName, String trackName, int duration, String albumURL, String artistURL, String releaseDate) {
        this.artistName = new SimpleStringProperty(artistName);
        this.trackName = new SimpleStringProperty(trackName);
        this.duration = new SimpleIntegerProperty(duration);
        this.albumURL = new SimpleStringProperty(albumURL);
        this.artistURL = new SimpleStringProperty(artistURL);
        this.releaseDate = new SimpleStringProperty(releaseDate);

    }

    public String getArtistName() {
        return artistName.get();
    }

    public String getTrackName() {
        return trackName.get();
    }

    public int getDuration() {
        return duration.get();
    }

    public String getAlbumURL() {
        return albumURL.get();
    }

    public String getArtistCover() {
        return artistURL.get();
    }

    public String getReleaseDate() {
        return releaseDate.get();
    }

}
