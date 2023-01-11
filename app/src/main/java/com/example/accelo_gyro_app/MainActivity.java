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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    private Sensor accelerometer, gyroscope;
    TextView xaccValue ,yaccValue, zaccValue, xgyrValue, ygyrValue, zgyrValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews to display sensor values
        xaccValue= (TextView) findViewById(R.id.xaccValue);
        yaccValue= (TextView) findViewById(R.id.yaccValue);
        zaccValue= (TextView) findViewById(R.id.zaccValue);
        xgyrValue=(TextView) findViewById(R.id.xgyrValue);
        ygyrValue=(TextView) findViewById(R.id.ygyrValue);
        zgyrValue=(TextView) findViewById(R.id.zgyrValue);

        // Initialize SensorManager and register listeners for accelerometer and gyroscope sensors
        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer listener");

        gyroscope= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(MainActivity.this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered gyroscope listener");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // This method is required, but not used in this app
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor=event.sensor;
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String data = timestamp + ",";
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            Log.d(TAG, "onSensorChanged: X:"+ event.values[0] + "Y:"+ event.values[1] +"Z:" + event.values[2]);
            xaccValue.setText("xValue: " + event.values[0]);
            yaccValue.setText("yValue: " + event.values[1]);
            zaccValue.setText("zValue: " + event.values[2]);
            data += "accelerometer," + event.values[0] + "," + event.values[1] + "," + event.values[2] + "\n";
        }else if(sensor.getType()== Sensor.TYPE_GYROSCOPE){
            xgyrValue.setText("aValue: " + event.values[0]);
            ygyrValue.setText("bValue: " + event.values[1]);
            zgyrValue.setText("cValue: " + event.values[2]);
            data += "gyroscope," + event.values[0] + "," + event.values[1] + "," + event.values[2] + "\n";
        }
        writeToFile(data);
    }

    private void writeToFile(String data) {
        try {
            FileOutputStream fos = openFileOutput("sensor_data.csv", Context.MODE_APPEND);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException");
            e.printStackTrace();
        }
    }
}

