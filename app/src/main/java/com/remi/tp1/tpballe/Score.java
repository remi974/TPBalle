package com.remi.tp1.tpballe;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Rémi on 24/10/2017.
 */
// This class represents a single entry (post) in the XML feed.
// It includes the data members "title," "link," and "summary."

public class Score {

    /**
     * Player Name.
     */
    private String joueur;

    /**
     * Player's Score
     */
    private String score;

    /**
     * The day he completed a game.
     */
    private String date;

    /**
     * The label we want to show on snippet
     */
    private String markerLabel;

    /**
     * The longitude when player completed the game.
     */
    private String longitude;

    /**
     * The longitude when player completed the game.
     */
    private String latitude;

    /**
     * Build a Score.
     * @param joueur Player's Name.
     * @param score Player's Score
     * @param date The day when player cleared the game.
     * @param data Marker's data. (Longitude, Latitude, Label)
     */
    public Score(String joueur, String score, String date, String[] data) {
        this.joueur = joueur;
        this.date = date;
        this.score = score;
        this.markerLabel = data[2];
        this.longitude = data[1];
        this.latitude = data[0];
    }

    /**
     * Build a Score.
     * @param joueur Player's Name.
     * @param score Player's Score
     * @param date The day when player cleared the game.
     */
    public Score(String joueur, String score, String date) {
        this.joueur = joueur;
        this.score = score;
        this.date = date;
    }

    /**
     * Getter for Player's Name
     * @return Player's Name
     */
    public String getJoueur() {
        return joueur;
    }

    /**
     * Getter for Player's score
     * @return Player's score
     */
    public String getScore() { return score; }

    /**
     * Getter for the Date on which the game was completed.
     * @return the Date on which the game was completed.
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter for the label on map's Marker.
     * @return the label on map's Marker.
     */
    public String getMarkerLabel() {
        return markerLabel;
    }

    /**
     * Getter for the longitude of player.
     * @return the longitude of player.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Getter for the longitude of player.
     * @return the longitude of player.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Format String to Get all Score data altogether.
     * @return
     */
    public String toString() {

        return new String("Nom du joueur " + this.joueur + ", Score=" + score + ", le : " + date + "." +
                "Latitude = " + latitude + ", Longitude = " + longitude + ", Label : " + markerLabel);
    }

    /**
     * Set the data for the label on map's Marker.
     * @param markerLabel the data for the label on map's Marker.
     */
    public void setMarkerLabel(String markerLabel) {
        this.markerLabel = markerLabel;
    }

    /**
     * Set the data for the Player's longitude.
     * @param longitude the data for the Player's longitude.
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Set the data for the Player's latitude.
     * @param latitude the data for the Player's latitude.
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
