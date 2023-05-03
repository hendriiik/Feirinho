package com.example.feirinho;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class ShakeDetector implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 20F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private long mShakeTimestamp;
    private Context ctx;

    protected boolean deveVerificarShake = true;

    public ShakeDetector(Context ctx) {

        this.deveVerificarShake = true;
        this.ctx = ctx;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!deveVerificarShake) {
            return;
        }

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
        Log.d("teste", "chacoalhou!");

        if (ctx instanceof MainActivity) {
            Intent it = new Intent(ctx, CapivaraAleatoriaActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(it);
            ((MainActivity) ctx).finish();
            Log.d("teste", "Iniciando CapivaraAleatoriaActivity");
        }
    }
}