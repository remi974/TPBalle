package com.remi.tp1.tpballe;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Score> alScores;
    private int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        alScores = new ArrayList<Score>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        position = extras.getInt("position");

        ScoresTPBalleXMLParser parser = new ScoresTPBalleXMLParser();

        try {
            alScores = parser.parse(getResources().openRawResource(R.raw.scores));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Log.i("Position", "Position cliquée : " + position);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        int i = -1;

        for (Score score : alScores) {
            i++;
            Marker marker = mMap.addMarker(
                    new MarkerOptions().position(
                        new LatLng( new Double(score.getLatitude()),
                                    new Double(score.getLongitude()))
                    ).title(score.getJoueur() + ", Score : " + score.getScore())
                    .snippet("Lat : " + score.getLatitude()
                            + ", Long : " + score.getLongitude()
                            + " le " + score.getDate())
            );

            if( position != -1 && position == i) {

                Log.i(  "Position" ,
                        "Position = " + position +
                                " Latitude = " + score.getLatitude() +
                                " Longitude = " + score.getLongitude());

                // Add a marker at current location and move the camera
                LatLng current_location = new LatLng(
                        new Double(score.getLatitude()),
                        new Double(score.getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(current_location));
                marker.showInfoWindow();
            }
        }
    }
}
