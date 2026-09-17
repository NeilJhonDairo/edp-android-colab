package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.hardware.rememberAccelerometer
import kotlin.math.abs

fun isLevel(x: Float, y: Float, tolerance: Float = 0.5f) =
    abs(x) < tolerance && abs(y) < tolerance

@Composable
fun LevelCard() {
    val v = rememberAccelerometer()
    val level = isLevel(v[0], v[1])

    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("Level check", style = MaterialTheme.typography.titleMedium)
            Text("x = %.2f   y = %.2f   z = %.2f".format(v[0], v[1], v[2]))
            Text(
                text = if (level) "LEVEL ✓" else "Tilted, adjust",
                color = if (level) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}