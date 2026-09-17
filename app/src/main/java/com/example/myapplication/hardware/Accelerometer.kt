package com.example.myapplication.hardware

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import kotlin.math.sqrt

@Composable
fun rememberAccelerometer(onShake: () -> Unit = {}): FloatArray {
    val context = LocalContext.current
    var values by remember { mutableStateOf(floatArrayOf(0f, 0f, 0f)) }
    var lastShakeTime by remember { mutableLongStateOf(0L) }

    LifecycleResumeEffect(Unit) {
        val sm = context.getSystemService(SensorManager::class.java)
        val sensor = sm?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val listener = object : SensorEventListener {
            override fun onSensorChanged(e: SensorEvent) {
                values = e.values.clone()

                val x = values[0]
                val y = values[1]
                val z = values[2]

                // Calculate G-force magnitude
                val gForce = sqrt(x * x + y * y + z * z) / SensorManager.GRAVITY_EARTH

                // Gi-down threshold to 1.05f para dali ra mo-trigger sa emulator
                if (gForce > 1.05f) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastShakeTime > 800) {
                        lastShakeTime = currentTime
                        Log.d("MainActivity", "Shake capture") // KINI ANG MO-RENDER SA LOGCAT
                        onShake()
                    }
                }
            }
            override fun onAccuracyChanged(s: Sensor?, accuracy: Int) = Unit
        }
        if (sensor != null) {
            sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        }

        onPauseOrDispose {
            sm?.unregisterListener(listener)
        }
    }
    return values
}