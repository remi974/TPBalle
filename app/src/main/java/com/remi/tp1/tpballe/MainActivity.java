package com.remi.tp1.tpballe;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener/*, View.OnClickListener*/ {

    /**
     * Objet permettant de gérer les interactions avec les différents types de capteurs sensoriels.
     */
    private SensorManager mSensorManager;

    /**
     * Le capteur Sensoriel utilisé (Accelerometer).
     */
    private Sensor mySensor;

    /**
     * Indique si le capteur est disponible ou non.
     */
    private boolean sensorSupported;

    /**
     * Permet de dessiner des formes à l'intérieur.
     */
    private ImageView image;

    /**
     * That's where you can draw things and animate them to play the game.
     */
    private AnimatedView mAnimatedView = null;

    /**
     * Called when starting app.
     * @param savedInstanceState Stores data when switching to portrait/landscape
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //Set Window in Portrait Mode with Fullscreen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Get the Acceloremeter Sensor for your App.
        mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Link the view with an attribute to draw.
        image = (ImageView) findViewById(R.id.imageView);
        //createBitMap();

        //Starts animation and/or game.
        mAnimatedView = new AnimatedView(this);

        //Set our content to a view, not like the traditional setting to a layout
        setContentView(mAnimatedView);

        //btn_toggle.setOnClickListener(this);

    }

    /**
     * Called when Sensor data varies.
     * @param sensorEvent Holds Sensor data
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        switch (sensorEvent.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:

                mAnimatedView.onSensorEvent(sensorEvent);
                break;

        }
    }

    /**
     * Called when you change acquisition rate.
     * @param sensor
     * @param i
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
/*
    @Override
    public void onClick(View view) {

        if(btn_toggle.isChecked()) {
            mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorSupported = mSensorManager.registerListener(this, mySensor,SensorManager.SENSOR_DELAY_NORMAL );
        }
        else {
            if (sensorSupported)
                mSensorManager.unregisterListener(this, mySensor);
        }
    }
*/

    /**
     * Called when app is active again.
     */
    @Override
    public void onResume() {
        super.onResume();
        //if(btn_toggle.isChecked()) {

            //mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorSupported = mSensorManager.registerListener(this, mySensor,SensorManager.SENSOR_DELAY_GAME );
        //}
    }

    /**
     * Called when your app runs in background or is stopped/paused. Idle mode.
     */
    @Override
    public void onPause() {


        //if (sensorSupported)
            mSensorManager.unregisterListener(this, mySensor);

        super.onPause();
    }

    /**
     *  Draw the ball on Canvas.
     */
    private void createBitMap() {

        Bitmap bitMap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);  //creates bmp
        bitMap = bitMap.copy(bitMap.getConfig(), true);     //lets bmp to be mutable
        Canvas canvas = new Canvas(bitMap);  //draw a canvas in defined bmp

        Paint paint = new Paint();
        // smooths
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4.5f);

        //Opacity
        //p.setAlpha(0x80); //
        canvas.drawCircle(50, 50, 30, paint);
        image.setImageBitmap(bitMap);
    }

    /**
     * Used to manage your menu linked with a layout you designed.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    /**
     * Called when you click an item on the menu.
     * @param item The Selected Item.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {

            //Starts a new game
            case R.id.new_game:

                return true;

            //Show a pop up on how to play.
            case R.id.help:

                return true;

            //Show a pop up about details in the game.

            case R.id.about:

                //Manage XML File for data persistancy. We need to store Scores of previous players.
                ScoresTPBalleXMLParser parser = new ScoresTPBalleXMLParser();
                Score score = new Score("Trump", "2", "21-10-2009");
                score.setLatitude("25");
                score.setLongitude("25");
                score.setMarkerLabel("");

                //Write XML File in ExternalStorage of Device.
                File f = new File(this.getExternalCacheDir() + "scores.xml");
                Log.d("File", this.getExternalCacheDir() + "scores.xml");
                parser.writeXMLData(score, f);
                return true;

            //Starts a new Activity when you click to visualize the scores.
            case R.id.scores:

                /*ScoresTPBalleXMLParser parser = new ScoresTPBalleXMLParser();
                List<Score> alScores = null;
                try {
                    alScores = parser.parse(getResources().openRawResource(R.raw.scores));
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(Score score :  alScores)
                    Log.i("Score", score.toString());
                */
                startScoresActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Launching new ScoresActivity. Show the details of previous players' scores.
     * */
    private void startScoresActivity() {
        Intent i = new Intent(MainActivity.this, ScoresActivity.class);
        startActivity(i);
    }
}
