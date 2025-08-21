package com.example.lavender.presentation

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PanicModeViewModel : ViewModel() {
    private val _isPanicActive = MutableStateFlow(false)
    val isPanicActive: StateFlow<Boolean> = _isPanicActive

    private var mediaPlayer: MediaPlayer? = null
    private var panicJob: Job? = null
    private var isCancelled = false

    fun togglePanicMode(context: Context) {
        if (_isPanicActive.value) {
            stopAlarm()
            _isPanicActive.value = false
        } else {
            initiatePanicCountdown(context)
        }
    }

    private fun initiatePanicCountdown(context: Context) {
        isCancelled = false
        panicJob?.cancel()  // Cancel any existing countdown

        panicJob = viewModelScope.launch {
            delay(3000) // 3-second delay before activation

            if (!isCancelled) {
                _isPanicActive.value = true
                startAlarm(context)
            }
        }
    }

    fun cancelPanic() {
        isCancelled = true
        panicJob?.cancel()
    }

    private fun startAlarm(context: Context) {
        stopAlarm() // Ensure any existing sound is stopped before playing a new one
        mediaPlayer = MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    fun stopAlarm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onCleared() {
        super.onCleared()
        stopAlarm()
    }
}
