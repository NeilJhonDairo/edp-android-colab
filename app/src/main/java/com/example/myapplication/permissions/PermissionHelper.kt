package com.example.myapplication.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LifecycleResumeEffect

enum class PermStatus { Granted, NeedsRationale, Denied, NotAsked }

class PermissionState(val status: PermStatus, val request: () -> Unit)

fun Context.openAppSettings() = startActivity(
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
)

fun Activity.permStatus(p: String, asked: Boolean): PermStatus {
    if (ContextCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_GRANTED) return PermStatus.Granted
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) return PermStatus.NeedsRationale
    if (asked) return PermStatus.Denied
    return PermStatus.NotAsked
}

@Composable
fun rememberPermission(permission: String): PermissionState {
    val activity = LocalActivity.current ?: error("No Activity found")
    var asked by rememberSaveable { mutableStateOf(false) }
    var status by remember { mutableStateOf(activity.permStatus(permission, asked)) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        asked = true
        status = activity.permStatus(permission, true)
    }

    LifecycleResumeEffect(permission) {
        status = activity.permStatus(permission, asked)
        onPauseOrDispose { }
    }

    return PermissionState(status) { launcher.launch(permission) }
}