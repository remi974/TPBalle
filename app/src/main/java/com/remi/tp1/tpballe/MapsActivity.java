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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /**
     * The Google Map used in Activity.
     */
    private GoogleMap mMap;

    /**
     * The scores of previous players.
     */
    private List<Score> alScores;

    /**
     * Indicates which item you clicked on the list.
     * -1 is default value when youdon't click.
     */
    private int position = -1;


    /**
     * Starts the Activity to show a Google Map.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Builds objects.
        alScores = new ArrayList<Score>();
        ScoresTPBalleXMLParser parser = new ScoresTPBalleXMLParser();

        //Get the item position in the list from intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        position = extras.getInt("position");

        //Retrieve data from XML File (Data persistancy).
        try {
            alScores = parser.parse(new FileInputStream(new File(this.getExternalCacheDir() + "scores.xml")));
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
        mMap.getUiSettings().setZoomControlsEnabled(true);//set +/- zoom on the map.
        int i = -1;

        //Show all markers on map based on data from XML
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

            //That's the marker we clicked on before.
            //Animate the Map to zoom/show current marker.
            if( position != -1 && position == i) {

                Log.i(  "Position" ,
                        "Position = " + position +
                                " Latitude = " + score.getLatitude() +
                                " Longitude = " + score.getLongitude());

                // Add a marker at current location and move the camera
                LatLng current_location = new LatLng(
                        new Double(score.getLatitude()),
                        new Double(score.getLongitude()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
                marker.showInfoWindow();
            }
        }
    }
}
