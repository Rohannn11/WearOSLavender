package com.example.lavender.presentation

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PanicModeButton(
    viewModel: PanicModeViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    val isPanicActive by viewModel.isPanicActive.collectAsState()
    var isCountingDown by remember { mutableStateOf(false) }
    var countdownProgress by remember { mutableStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            if (isCountingDown) {
                // Cancel panic mode within 3 seconds
                isCountingDown = false
                countdownProgress = 1f
            } else if (!isPanicActive) {
                isCountingDown = true
                coroutineScope.launch {
                    for (i in 3 downTo 1) {
                        countdownProgress = i / 3f
                        delay(1000)
                    }
                    if (isCountingDown) {
                        viewModel.togglePanicMode(context)
                        isCountingDown = false
                        countdownProgress = 1f
                    }
                }
            } else {
                viewModel.togglePanicMode(context)
            }
        },
        modifier = modifier
            .size(80.dp)
            .padding(4.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = when {
                isPanicActive -> Color.Red
                isCountingDown -> Color.Yellow
                else -> Color.DarkGray
            }
        )
    ) {
        if (isCountingDown) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = 1f - countdownProgress,
                    modifier = Modifier.fillMaxSize(),
                    indicatorColor = Color.White
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Panic Mode",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isPanicActive) "Stop" else "Panic",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
