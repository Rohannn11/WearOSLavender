package com.example.lavender.presentation

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*

@Composable
fun PanicModeButton(
    viewModel: PanicModeViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    val isPanicActive by viewModel.isPanicActive.collectAsState()

    Button(
        onClick = { viewModel.togglePanicMode(context) },
        modifier = modifier
            .size(80.dp)
            .padding(4.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isPanicActive) Color.Red else Color.DarkGray
        )
    ) {
        Text(
            text = if (isPanicActive) "Stop\nAlarm" else "Panic\nMode",
            textAlign = TextAlign.Center
        )
    }
}

