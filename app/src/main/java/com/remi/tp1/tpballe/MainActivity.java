package com.remi.tp1.tpballe;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    /**
     * Objet permettant de gérer les interactions avec les différents types de capteurs sensoriels.
     */
    private SensorManager mSensorManager;

    /**
     * Le capteur Sensoriel utilisé (ici le Gyroscope).
     */
    private Sensor mySensor;

    /**
     * Indique si le capteur est disponible ou non.
     */
    private boolean sensorSupported;

    /**
     * Le Bouton ON/OFF de la vue principale.
     */
    private ToggleButton btn_toggle;

    /**
     * Le bouton permettant d'augmenter la vitesse de la balle.
     */
    private Button btn_plus;

    /**
     * Le bouton permettant de réduire la vitesse de la balle.
     */
    private Button btn_moins;

    /**
     * Permet de dessiner des formes à l'intérieur.
     */
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.btn_toggle = (ToggleButton) findViewById(R.id.btn_toggle);
        this.btn_plus = (Button) findViewById(R.id.btn_plus);
        this.btn_moins = (Button) findViewById(R.id.btn_moins);

        mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        image = (ImageView) findViewById(R.id.imageView);
        createBitMap();

        btn_toggle.setOnClickListener(this);
    }

    /**
     *
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        switch (sensorEvent.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:

                //Log.d("X", Float.toString(sensorEvent.values[0]) + " m/s^2");
                //Log.d("Y", Float.toString(sensorEvent.values[1]) + " m/s^2");
                //Log.d("Z", Float.toString(sensorEvent.values[2]) + " m/s^2");

                float[] g = new float[3];
                g = sensorEvent.values.clone();

                float norm_Of_g = new Double(Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2])).floatValue();

                // Normalize the accelerometer vector
                g[0] = g[0] / norm_Of_g;
                g[1] = g[1] / norm_Of_g;
                g[2] = g[2] / norm_Of_g;

                int inclination = (int) Math.round(Math.toDegrees(Math.acos(g[2])));
                Log.d("Inclination", "" + inclination);
                if (inclination < 25 || inclination > 155)
                {
                    // device is flat
                }
                else
                {
                    // device is not flat
                    int rotation = (int) Math.round(Math.toDegrees(Math.atan2(g[0], g[1])));
                    Log.d("Rotation", rotation + "°" );
                }

                break;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

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

    @Override
    public void onResume() {
        super.onResume();
        if(btn_toggle.isChecked()) {

            mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorSupported = mSensorManager.registerListener(this, mySensor,SensorManager.SENSOR_DELAY_NORMAL );
        }
    }


    @Override
    public void onPause() {
        if (sensorSupported)
            mSensorManager.unregisterListener(this, mySensor);
        super.onPause();
    }

    /**
     *
     */
    private void createBitMap() {

        Bitmap bitMap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);  //creates bmp
        bitMap = bitMap.copy(bitMap.getConfig(), true);     //lets bmp to be mutable
        Canvas canvas = new Canvas(bitMap);                 //draw a canvas in defined bmp

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
}
