package com.example.feirinho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtNomeCurso = findViewById(R.id.txtNomeCurso);
        ConstraintLayout clPlanoDeFundo = findViewById(R.id.clPlanoDeFundo);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ShakeDetector shakeDetector = new ShakeDetector(getBaseContext(), txtNomeCurso, clPlanoDeFundo);
        sensorManager.registerListener(shakeDetector, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
    }
}