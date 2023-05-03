package com.example.feirinho;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class ShakeDetector implements SensorEventListener {

    private static int QUANTIDADE_CURSOS = 4;
    private static final float SHAKE_THRESHOLD_GRAVITY = 20F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private long mShakeTimestamp;

    private TextView txtNomeCurso;
    private ConstraintLayout clPlanoDeFundo;
    private Context ctx;

    public ShakeDetector(Context ctx, TextView txtNomeCurso, ConstraintLayout clPlanoDeFundo) {
        this.txtNomeCurso = txtNomeCurso;
        this.clPlanoDeFundo = clPlanoDeFundo;
        this.ctx = ctx;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gravityForce = (float) Math.sqrt(x * x + y * y + z * z);
            if (gravityForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }
                mShakeTimestamp = now;
                onShake();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //???
    }

    public void onShake() {
        Log.d("teste","chacoalhou!");
        Random random = new Random();
        int stringId = ctx.getResources().getIdentifier("curso"+(random.nextInt(QUANTIDADE_CURSOS-1)+1), "string", ctx.getPackageName());
        String nomeCurso = ctx.getResources().getString(stringId);
        txtNomeCurso.setText(nomeCurso);

        //clPlanoDeFundo.setBackgroundColor();
    }
}