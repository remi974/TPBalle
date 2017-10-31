package com.remi.tp1.tpballe;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Rémi on 24/10/2017.
 */
// This class represents a single entry (post) in the XML feed.
// It includes the data members "title," "link," and "summary."

public class Score {

    private String joueur;
    private String score;
    private String date;
    private String markerLabel;
    private String longitude;
    private String latitude;
    private int color;


    public Score(String joueur, String score, String date, String[] data) {
        this.joueur = joueur;
        this.date = date;
        this.score = score;
        this.markerLabel = data[2];
        this.longitude = data[1];
        this.latitude = data[0];
    }

    public Score(String joueur, String score, String date) {
        this.joueur = joueur;
        this.score = score;
        this.date = date;
    }

    public String getJoueur() {
        return joueur;
    }

    public String getScore() { return score; }

    public String getDate() {
        return date;
    }

    public int getColor() {
        return color;
    }

    public String getMarkerLabel() {
        return markerLabel;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String toString() {

        return new String("Nom du joueur " + this.joueur + ", Score=" + score + ", le : " + date + "." +
                "Latitude = " + latitude + ", Longitude = " + longitude + ", Label : " + markerLabel);
    }

    public void setMarkerLabel(String markerLabel) {
        this.markerLabel = markerLabel;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /*@Override
    public int compareTo(@NonNull Score score) {
        return new Integer(this.score).compareTo(new Integer(score.getScore()));
    }*/
}
