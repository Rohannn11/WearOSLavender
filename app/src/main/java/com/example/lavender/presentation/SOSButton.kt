package com.example.lavender.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text

@Composable
fun SOSButton(
    viewModel: SOSViewModel,
    currentLocation: String,
    modifier: Modifier = Modifier
) {
    val sosState by viewModel.sosState.collectAsState()

    Button(
        onClick = {
            when (sosState) {
                is SOSViewModel.SOSState.Idle -> viewModel.initiateSOSSequence(currentLocation)
                else -> viewModel.cancelSOS()
            }
        },
        modifier = modifier
            .size(80.dp)
            .padding(4.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = when (sosState) {
                is SOSViewModel.SOSState.Confirming -> Color.Red
                is SOSViewModel.SOSState.Sending -> Color.Yellow
                is SOSViewModel.SOSState.Sent -> Color.Green
                is SOSViewModel.SOSState.Error -> Color.Red
                else -> Color.DarkGray
            }
        )
    ) {
        Text(
            text = when (sosState) {
                is SOSViewModel.SOSState.Confirming ->
                    "Cancel\n${(sosState as SOSViewModel.SOSState.Confirming).remainingSeconds}s"
                is SOSViewModel.SOSState.Sending -> "Sending\nSOS..."
                is SOSViewModel.SOSState.Sent -> "SOS\nSent!"
                is SOSViewModel.SOSState.Error -> "Error!"
                else -> "SOS"
            },
            textAlign = TextAlign.Center
        )
    }
}