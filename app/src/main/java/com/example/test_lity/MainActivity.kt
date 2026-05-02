// MainActivity.kt
package com.example.test_lity

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        setContent {
            MaterialTheme {
                var pairedDevices by remember { mutableStateOf<List<BluetoothDevice>>(emptyList()) }
                var connectedAddresses by remember { mutableStateOf(setOf<String>()) }
                val context = LocalContext.current

                val requestPermissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    val allGranted = permissions.all { it.value }
                    if (allGranted) {
                        loadBluetoothDevices(bluetoothAdapter, context) { paired, connected ->
                            pairedDevices = paired
                            connectedAddresses = connected
                        }
                    } else {
                        // Handle the case where permissions are not granted
                        // e.g., show a message to the user
                    }
                }

                LaunchedEffect(Unit) {
                    if (PermissionManager.hasBluetoothPermissions(context)) {
                        loadBluetoothDevices(bluetoothAdapter, context) { paired, connected ->
                            pairedDevices = paired
                            connectedAddresses = connected
                        }
                    } else {
                        requestPermissionLauncher.launch(PermissionManager.bluetoothPermissions.toTypedArray())
                    }
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    DeviceList(pairedDevices, connectedAddresses)
                }
            }
        }
    }

    private fun loadBluetoothDevices(
        adapter: BluetoothAdapter?,
        context: Context,
        onLoaded: (List<BluetoothDevice>, Set<String>) -> Unit
    ) {
        if (adapter == null || !PermissionManager.hasBluetoothPermissions(context)) {
            onLoaded(emptyList(), emptySet())
            return
        }

        val paired = adapter.bondedDevices?.toList() ?: emptyList()
        var connected = setOf<String>()

        adapter.getProfileProxy(context, object : BluetoothProfile.ServiceListener {
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                proxy?.connectedDevices?.let {
                    connected = it.map { device -> device.address }.toSet()
                    onLoaded(paired, connected)
                }
            }

            override fun onServiceDisconnected(profile: Int) {}
        }, BluetoothProfile.A2DP)
    }
}

@Composable
fun DeviceList(pairedDevices: List<BluetoothDevice>, connectedAddresses: Set<String>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Paired Bluetooth Devices", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(pairedDevices) { device ->
                val name = device.name?.takeIf { it.isNotEmpty() } ?: "Unknown device"
                val status = if (connectedAddresses.contains(device.address)) "Connected" else "Paired only"
                Text(
                    "$name - $status",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}