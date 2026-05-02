package com.example.test_lity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionManager {

    val bluetoothPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.BLUETOOTH_CONNECT,
        )
    } else {
        listOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
        )
    }

    fun hasBluetoothPermissions(context: Context): Boolean {
        return bluetoothPermissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissions(
        permissionLauncher: ActivityResultLauncher<Array<String>>,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        // This function will be more complex because it handles a list of permissions
        // For simplicity here, we'll just check the main one.
        // A more robust implementation would check the result of each permission.

        // This part needs to be handled within the ActivityResultLauncher contract.
        // The composable will launch the request, and the launcher's callback will handle the result.
        // This function is mainly for checking if a request is needed.
    }
}