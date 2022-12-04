package com.example.accelo_gyro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    private Sensor acclerometer, mGyro;

    TextView xaccValue ,yaccValue, zaccValue, xgyrValue, ygyrValue, zgyrValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xaccValue= (TextView) findViewById(R.id.xaccValue);
        yaccValue= (TextView) findViewById(R.id.yaccValue);
        zaccValue= (TextView) findViewById(R.id.zaccValue);

        xgyrValue=(TextView) findViewById(R.id.xgyrValue);
        ygyrValue=(TextView) findViewById(R.id.ygyrValue);
        zgyrValue=(TextView) findViewById(R.id.zgyrValue);

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acclerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,acclerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer listener");

        mGyro= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(MainActivity.this,mGyro, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered gyroscope listener");

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor=event.sensor;
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            Log.d(TAG, "onSensorChanged: X:"+ event.values[0] + "Y:"+ event.values[1] +"Z:" + event.values[2]);

            xaccValue.setText("xValue: " + event.values[0]);
            yaccValue.setText("yValue: " + event.values[1]);
            zaccValue.setText("zValue: " + event.values[2]);
        }else if(sensor.getType()== Sensor.TYPE_GYROSCOPE){
            xgyrValue.setText("aValue: " + event.values[0]);
            ygyrValue.setText("bValue: " + event.values[1]);
            zgyrValue.setText("cValue: " + event.values[2]);
        }

    }
}