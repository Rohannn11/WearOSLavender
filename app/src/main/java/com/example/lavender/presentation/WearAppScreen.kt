package com.example.lavender.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.*
import androidx.wear.compose.material3.IconButton
@Composable
fun WearAppScreen(
    locationViewModel: LocationViewModel,
    sosViewModel: SOSViewModel,
    contactsViewModel: ContactsViewModel,
    panicModeViewModel: PanicModeViewModel,
    heartRateViewModel: HeartRateViewModel,
    speedTrackingViewModel: SpeedTrackingViewModel
) {
    var showLocation by remember { mutableStateOf(false) }
    var showAlerts by remember { mutableStateOf(false) }
    var showContacts by remember { mutableStateOf(false) }

    val location by locationViewModel.locationState.collectAsState()
    val isTracking by locationViewModel.isTracking.collectAsState()

    when {
        showAlerts -> AlertScreen { showAlerts = false }
        showLocation -> LocationScreen(locationViewModel) { showLocation = false }
        showContacts -> ContactsScreen(contactsViewModel) { showContacts = false }
        else -> MainScreen(
            onShowAlerts = { showAlerts = true },
            onShowLocation = { showLocation = true },
            onShowContacts = { showContacts = true },
            locationViewModel = locationViewModel,
            sosViewModel = sosViewModel,
            panicModeViewModel = panicModeViewModel,
            heartRateViewModel = heartRateViewModel,
            speedTrackingViewModel = speedTrackingViewModel,
            currentLocation = location,
            contactsViewModel = contactsViewModel,
            isTracking = isTracking
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
    onShowContacts: () -> Unit,
    locationViewModel: LocationViewModel,
    sosViewModel: SOSViewModel,
    panicModeViewModel: PanicModeViewModel,
    heartRateViewModel: HeartRateViewModel,
    speedTrackingViewModel: SpeedTrackingViewModel,
    currentLocation: String,
    isTracking: Boolean,
    contactsViewModel: ContactsViewModel
) {
    val context = LocalContext.current
    val heartRate by heartRateViewModel.heartRate.observeAsState(initial = 0f)
    val alertMessage by heartRateViewModel.alertMessage.observeAsState()

    val currentSpeed by speedTrackingViewModel.currentSpeed.collectAsState()
    val speedAlert by speedTrackingViewModel.speedAlert.collectAsState()
    val alertCount by speedTrackingViewModel.alertCount.collectAsState()

    var isHeartRateTracking by remember { mutableStateOf(false) }
    var isSpeedTracking by remember { mutableStateOf(false) }

    // Monitor alert count and trigger SOS
    LaunchedEffect(alertCount) {
        if (alertCount >= 3) {
            sosViewModel.initiateSOSSequence(currentLocation)
            speedTrackingViewModel.resetAlertCount()
        }
    }

    Scaffold(
        timeText = { TimeText() },
        modifier = Modifier.background(Color.Black)
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SOSButton(
                    viewModel = sosViewModel,
                    currentLocation = currentLocation,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            item {
                PanicModeButton(
                    viewModel = panicModeViewModel,
                    context = context,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            item {
                CustomButton(
                    text = if (isTracking) "Stop Tracking" else "Start Tracking",
                    icon = Icons.Default.LocationOn,
                    onClick = {
                        locationViewModel.toggleTracking()
                        Toast.makeText(
                            context,
                            if (isTracking) "Tracking stopped" else "Tracking started",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

            item {
                CustomButton(
                    text = "Contacts",
                    icon = Icons.Default.ContactPhone,
                    onClick = onShowContacts
                )
            }

            item {
                CustomButton(
                    text = "Alerts",
                    icon = Icons.Default.Warning,
                    onClick = onShowAlerts
                )
            }

            // Heart Rate Section
            item {
                Text(
                    text = "Heart Rate: ${heartRate.toInt()} BPM",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            alertMessage?.let {
                item {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            item {
                CustomButton(
                    text = if (isHeartRateTracking) "Stop Heart Rate" else "Start Heart Rate",
                    icon = Icons.Default.Favorite,
                    onClick = {
                        heartRateViewModel.toggleHeartRateMonitoring()
                        isHeartRateTracking = !isHeartRateTracking
                        Toast.makeText(
                            context,
                            if (isHeartRateTracking) "Heart Rate Monitoring Started" else "Heart Rate Monitoring Stopped",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

            // Speed Tracking Section
            item {
                Text(
                    text = "Current Speed: ${String.format("%.1f", currentSpeed)} mph",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            speedAlert?.let {
                item {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            item {
                Text(
                    text = "Alert Count: $alertCount/3",
                    color = if (alertCount > 1) Color.Red else Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }

            item {
                CustomButton(
                    text = if (isSpeedTracking) "Stop Speed Tracking" else "Start Speed Tracking",
                    icon = Icons.Default.Speed,
                    onClick = {
                        if (isSpeedTracking) {
                            speedTrackingViewModel.stopSpeedTracking()
                        } else {
                            speedTrackingViewModel.startSpeedTracking()
                        }
                        isSpeedTracking = !isSpeedTracking
                        Toast.makeText(
                            context,
                            if (isSpeedTracking) "Speed tracking started" else "Speed tracking stopped",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}

@Composable
fun CustomButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Gray,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}