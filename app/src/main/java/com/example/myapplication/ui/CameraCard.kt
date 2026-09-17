package com.example.myapplication.ui

import android.Manifest
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.hardware.*
import com.example.myapplication.permissions.rememberPermission
import java.io.File

@Composable
fun CameraCard(shakeTrigger: Int = 0) {
    val context = LocalContext.current
    val cameraPermission = rememberPermission(Manifest.permission.CAMERA)
    val capture = remember { ImageCapture.Builder().build() }
    var photo by remember { mutableStateOf<File?>(null) }

    LaunchedEffect(shakeTrigger) {
        if (shakeTrigger > 0) {
            Log.d("MainActivity", "Shake capture")
            takePhoto(context, capture) { saved -> photo = saved }
        }
    }

    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Field photo", style = MaterialTheme.typography.titleMedium)
            PermissionGate(
                state = cameraPermission,
                feature = "Camera",
                reason = "We need the camera to photograph the issue you report."
            ) {
                CameraPreview(
                    capture = capture,
                    modifier = Modifier.fillMaxWidth().height(240.dp)
                )

                Button(onClick = {
                    takePhoto(context, capture) { saved -> photo = saved }
                }) {
                    Text("Take photo")
                }

                photo?.let { Text("Saved: ${it.name}") }
            }

            photo?.let { f ->
                val thumb = remember(f) { loadThumb(f) }
                thumb?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Last photo",
                        modifier = Modifier.size(96.dp)
                    )
                }
            }
        }
    }
}