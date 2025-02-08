package com.example.lavender.presentation

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class HeartRateViewModel(application: Application) : AndroidViewModel(application) {
    private val _heartRate = MutableLiveData(0f)
    val heartRate: LiveData<Float> = _heartRate

    private val _alertMessage = MutableLiveData<String?>()
    val alertMessage: LiveData<String?> = _alertMessage

    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())

    private val abnormalHeartRateThreshold = 100  // Set threshold

    fun toggleHeartRateMonitoring() {
        if (isTracking) {
            stopHeartRateMonitoring()
        } else {
            startHeartRateMonitoring()
        }
        isTracking = !isTracking
    }

    private fun startHeartRateMonitoring() {
        Toast.makeText(getApplication(), "Simulated Heart Rate Started", Toast.LENGTH_SHORT).show()
        simulateHeartRate()
    }

    private fun simulateHeartRate() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                val simulatedHeartRate = Random.nextInt(60, 120).toFloat()
                _heartRate.postValue(simulatedHeartRate)

                // Check for abnormal heart rate
                if (simulatedHeartRate > abnormalHeartRateThreshold) {
                    _alertMessage.postValue("Heart rate abnormally high: $simulatedHeartRate BPM")
                } else {
                    _alertMessage.postValue(null) // Reset alert if normal
                }

                if (isTracking) handler.postDelayed(this, 1000) // Update every second
            }
        }, 1000)
    }

    private fun stopHeartRateMonitoring() {
        handler.removeCallbacksAndMessages(null)
        _heartRate.postValue(0f) // Reset heart rate when stopped
        _alertMessage.postValue(null) // Clear any alerts
        Toast.makeText(getApplication(), "Heart Rate Monitoring Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onCleared() {
        super.onCleared()
        stopHeartRateMonitoring()
    }
}
