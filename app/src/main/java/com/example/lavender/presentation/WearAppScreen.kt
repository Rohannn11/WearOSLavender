package com.example.lavender.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.*
import androidx.wear.compose.material3.IconButton

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
            Icon(imageVector = Icons.Default.Warning, contentDescription = "Alert", modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = alertText, fontSize = 14.sp)
        }
    }
}
