package com.example.lavender.presentation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.*
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material3.Text
import kotlinx.coroutines.launch
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun LocationScreen(viewModel: LocationViewModel, onBack: () -> Unit) {
    val location by viewModel.locationState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Current Location:", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = location, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularButton(icon = Icons.Default.LocationOn) {
                viewModel.initialize(viewModel.fusedLocationClient!!)
            }
            CircularButton(icon = Icons.Default.Warning, onClick = onBack)
        }
    }
}


