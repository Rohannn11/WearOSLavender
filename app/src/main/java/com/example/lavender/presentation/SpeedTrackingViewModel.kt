package com.example.lavender.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class SpeedTrackingViewModel : ViewModel() {
    private val _currentSpeed = MutableStateFlow(0f)
    val currentSpeed: StateFlow<Float> = _currentSpeed

    private val _alertCount = MutableStateFlow(0)
    val alertCount: StateFlow<Int> = _alertCount

    private val _speedAlert = MutableStateFlow<String?>(null)
    val speedAlert: StateFlow<String?> = _speedAlert

    private var speedSimulationJob: Job? = null
    private val maxNormalSpeed = 25f // mph

    fun startSpeedTracking() {
        speedSimulationJob?.cancel()
        speedSimulationJob = viewModelScope.launch {
            while (true) {
                simulateSpeed()
                delay(2000) // Update speed every 2 seconds
            }
        }
    }

    fun stopSpeedTracking() {
        speedSimulationJob?.cancel()
        _currentSpeed.value = 0f
        _speedAlert.value = null
    }

    private fun simulateSpeed() {
        // Simulate speed between 0 and 40 mph
        val newSpeed = Random.nextFloat() * 40f
        _currentSpeed.value = newSpeed

        if (newSpeed > maxNormalSpeed) {
            _speedAlert.value = "Abnormal speed detected: ${String.format("%.1f", newSpeed)} mph"
            _alertCount.value += 1
        } else {
            _speedAlert.value = null
        }
    }

    fun resetAlertCount() {
        _alertCount.value = 0
    }
}