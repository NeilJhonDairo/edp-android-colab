package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.hardware.rememberAccelerometer
import com.example.myapplication.ui.CameraCard
import com.example.myapplication.ui.LevelCard
import com.example.myapplication.ui.LocationCard
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val accelValues = rememberAccelerometer()
                var shakeTriggerCount by remember { mutableIntStateOf(0) }
                var lastShakeTime by remember { mutableLongStateOf(0L) }

                LaunchedEffect(accelValues) {
                    val x = accelValues[0]
                    val y = accelValues[1]
                    val z = accelValues[2]

                    val gForce = sqrt((x * x + y * y + z * z).toDouble()) / 9.81
                    val currentTime = System.currentTimeMillis()

                    if (gForce > 1.5 && (currentTime - lastShakeTime > 800)) {
                        lastShakeTime = currentTime
                        shakeTriggerCount++
                    }
                }

                Scaffold { inner ->
                    Column(
                        Modifier
                            .padding(inner)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "LiceoFieldKit",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        LevelCard()
                        CameraCard(shakeTrigger = shakeTriggerCount)
                        LocationCard()
                    }
                }
            }
        }
    }
}