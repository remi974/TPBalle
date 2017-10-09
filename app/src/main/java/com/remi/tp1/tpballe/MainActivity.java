package com.remi.tp1.tpballe;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private SensorManager mSensorManager;
    private Sensor mySensor;
    private boolean sensorSupported;
    private ToggleButton btn_toggle;
    private Button btn_plus, btn_moins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.btn_toggle = (ToggleButton) findViewById(R.id.btn_toggle);
        this.btn_plus = (Button) findViewById(R.id.btn_plus);
        this.btn_moins = (Button) findViewById(R.id.btn_moins);

        mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        switch (sensorEvent.sensor.getType()) {

            case Sensor.TYPE_GYROSCOPE:

                break;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onClick(View view) {

        if(btn_toggle.isChecked()) {
            mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
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

            mySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorSupported = mSensorManager.registerListener(this, mySensor,SensorManager.SENSOR_DELAY_NORMAL );
        }
    }

    @Override
    public void onPause() {
        if (sensorSupported)
            mSensorManager.unregisterListener(this, mySensor);
        super.onPause();
    }
}
