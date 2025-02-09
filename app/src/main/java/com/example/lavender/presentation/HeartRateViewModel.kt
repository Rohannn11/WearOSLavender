package com.example.lavender.presentation

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HeartRateViewModel(
    application: Application,
    private val sosViewModel: SOSViewModel
) : AndroidViewModel(application) {
    private val _heartRate = MutableLiveData(0f)
    val heartRate: LiveData<Float> = _heartRate

    private val _alertMessage = MutableLiveData<String?>()
    val alertMessage: LiveData<String?> = _alertMessage

    private val _alertCount = MutableStateFlow(0)
    val alertCount: StateFlow<Int> = _alertCount

    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())

    private val abnormalHeartRateThreshold = 100
    private val maxAlertCountBeforeSOS = 3

    fun toggleHeartRateMonitoring() {
        if (isTracking) {
            stopHeartRateMonitoring()
        } else {
            startHeartRateMonitoring()
        }
        isTracking = !isTracking
    }

    private fun startHeartRateMonitoring() {
        simulateHeartRate()
    }

    private fun simulateHeartRate() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                val simulatedHeartRate = Random.nextInt(60, 120).toFloat()
                _heartRate.postValue(simulatedHeartRate)

                if (simulatedHeartRate > abnormalHeartRateThreshold) {
                    _alertMessage.postValue("Abnormal heart rate: $simulatedHeartRate BPM")
                    _alertCount.value += 1

                    if (_alertCount.value >= maxAlertCountBeforeSOS) {
                        triggerSOS()
                    }
                } else {
                    _alertMessage.postValue(null)
                }

                if (isTracking) handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    private fun triggerSOS() {
        viewModelScope.launch {
            sosViewModel.initiateSOSSequence("Last Known Location")
            resetAlertCount()
        }
    }

    fun resetAlertCount() {
        _alertCount.value = 0
    }

    private fun stopHeartRateMonitoring() {
        handler.removeCallbacksAndMessages(null)
        _heartRate.postValue(0f)
        _alertMessage.postValue(null)
        resetAlertCount()
    }

    override fun onCleared() {
        super.onCleared()
        stopHeartRateMonitoring()
    }
}
