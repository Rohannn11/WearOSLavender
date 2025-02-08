package com.example.lavender.presentation

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PanicModeViewModel : ViewModel() {
    private val _isPanicActive = MutableStateFlow(false)
    val isPanicActive: StateFlow<Boolean> = _isPanicActive

    private var mediaPlayer: MediaPlayer? = null

    fun togglePanicMode(context: Context) {
        if (_isPanicActive.value) {
            stopAlarm()
        } else {
            startAlarm(context)
        }
        _isPanicActive.value = !_isPanicActive.value
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
