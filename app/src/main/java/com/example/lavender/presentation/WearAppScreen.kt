package com.example.lavender.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.*
import androidx.wear.compose.material3.IconButton

@Composable
fun WearAppScreen(
    locationViewModel: LocationViewModel,
    sosViewModel: SOSViewModel
) {
    var showLocation by remember { mutableStateOf(false) }
    var showAlerts by remember { mutableStateOf(false) }
    val location by locationViewModel.locationState.collectAsState()
    val isTracking by locationViewModel.isTracking.collectAsState()

    val context = LocalContext.current // ✅ Get Context without passing it explicitly

    when {
        showAlerts -> AlertScreen { showAlerts = false }
        showLocation -> LocationScreen(locationViewModel) { showLocation = false }
        else -> MainScreen(
            onShowAlerts = { showAlerts = true },
            onShowLocation = { showLocation = true },
            locationViewModel = locationViewModel,
            sosViewModel = sosViewModel,
            currentLocation = location,
            isTracking = isTracking,
            context = context // ✅ Pass obtained context
        )
    }
}

@Composable
fun AlertScreen(onBack: () -> Unit) {
    val alerts = listOf(
        "High Traffic in your area!",
        "Heart Rate is unusually high!",
        "Severe Weather Alert: Storm Incoming!",
        "Security Alert: Check Nearby Activity!",
        "Heatwave Warning: Stay Hydrated!"
    )

    Scaffold(
        timeText = { TimeText() }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "Back")
                }
            }

            items(alerts) { alert ->
                AlertCard(alert)
            }
        }
    }
}

@Composable
fun AlertCard(alertText: String) {
    Card(
        onClick = {},
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Alert",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = alertText, fontSize = 14.sp)
        }
    }
}



@Composable
fun MainScreen(
    onShowAlerts: () -> Unit,
    onShowLocation: () -> Unit,
    locationViewModel: LocationViewModel,
    sosViewModel: SOSViewModel,
    currentLocation: String,
    isTracking: Boolean,
    context: Context // ✅ No more missing context error
) {
    Scaffold(
        timeText = { TimeText() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // SOS Button at the top
            SOSButton(
                viewModel = sosViewModel,
                currentLocation = currentLocation,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Location Tracking Toggle
            Button(
                onClick = {
                    locationViewModel.toggleTracking()
                    val message = if (isTracking) "Tracking Stopped" else "Tracking Started"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show() // ✅ Now works
                },
                modifier = Modifier.padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(
                    text = if (isTracking) "Stop Tracking" else "Start Tracking",
                    textAlign = TextAlign.Center
                )
            }

            // Original alert and location buttons
            CircularButton(
                icon = Icons.Default.Warning,
                onClick = onShowAlerts,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            CircularButton(
                icon = Icons.Default.LocationOn,
                onClick = onShowLocation,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun CircularButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(50.dp),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
