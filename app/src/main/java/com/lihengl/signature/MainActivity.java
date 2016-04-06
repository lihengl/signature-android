package com.lihengl.signature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gravity;
    private Sensor gyroscope;
    private Sensor uncalibratedGyroscope;
    private Sensor rotation;

    private boolean allSensorsAvailable() {
        if (uncalibratedGyroscope == null) { return false; }
        if (accelerometer == null) { return false; }
        if (gyroscope == null) { return false; }
        if (rotation == null) { return false; }
        if (gravity == null) { return false; }
        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("AccuracyChanged", sensor.toString());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d("Acceleration X", String.valueOf(event.values[0]));
            Log.d("Acceleration Y", String.valueOf(event.values[1]));
            Log.d("Acceleration Z", String.valueOf(event.values[2]));
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
            Log.d("Raw rate of rotation X", String.valueOf(event.values[0]));
            Log.d("Raw rate of rotation Y", String.valueOf(event.values[1]));
            Log.d("Raw rate of rotation Z", String.valueOf(event.values[2]));
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            Log.d("Rate of rotation X", String.valueOf(event.values[0]));
            Log.d("Rate of rotation Y", String.valueOf(event.values[1]));
            Log.d("Rate of rotation Z", String.valueOf(event.values[2]));
        } else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            Log.d("Rotation X", String.valueOf(event.values[0]));
            Log.d("Rotation Y", String.valueOf(event.values[1]));
            Log.d("Rotation Z", String.valueOf(event.values[2]));
            Log.d("Rotation S", String.valueOf(event.values[3]));
        } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            Log.d("Gravity X", String.valueOf(event.values[0]));
            Log.d("Gravity Y", String.valueOf(event.values[1]));
            Log.d("Gravity Z", String.valueOf(event.values[2]));
        } else {
            Log.d("Unknown Sensor", event.sensor.getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, uncalibratedGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, rotation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        uncalibratedGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        if (!allSensorsAvailable()) {
            Log.e("Incapable", "Required sensor unavailable");
        } else {
            Log.d("Created", "Hello!");
        }

    }
}
