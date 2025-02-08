package com.example.lavender.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel = ViewModelProvider(this)[LocationViewModel::class.java]

        requestLocationPermissions()

        setContent {
            WearAppView(viewModel)
        }
    }

    private fun requestLocationPermissions() {
        val requiredPermissions = mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION)
        requiredPermissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            Log.d("LocationDebug", "All permissions granted, initializing location tracking")
            initializeLocationTracking()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.values.all { it }
            if (granted) {
                Log.d("LocationDebug", "All location permissions granted")
                initializeLocationTracking()
            } else {
                Log.e("LocationDebug", "Location permission denied")
                Toast.makeText(this, "Location permission denied!", Toast.LENGTH_SHORT).show()
            }
        }

    @Suppress("MissingPermission")
    private fun initializeLocationTracking() {
        viewModel.initialize(fusedLocationProviderClient)
    }
}


@Composable
fun WearAppView(viewModel: LocationViewModel) {
    var showLocation by remember { mutableStateOf(false) }
    var showAlerts by remember { mutableStateOf(false) }

    when {
        showAlerts -> AlertScreen { showAlerts = false }
        showLocation -> LocationScreen(viewModel) { showLocation = false }
        else -> MainScreen(onShowAlerts = { showAlerts = true }, onShowLocation = { showLocation = true })
    }
}

@Composable
fun MainScreen(onShowAlerts: () -> Unit, onShowLocation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularButton(icon = Icons.Default.Warning, onClick = onShowAlerts)
        Spacer(modifier = Modifier.height(16.dp))
        CircularButton(icon = Icons.Default.LocationOn, onClick = onShowLocation)
    }
}

@Composable
fun CircularButton(icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        modifier = Modifier.size(60.dp),
        colors = ButtonDefaults.buttonColors()
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}
