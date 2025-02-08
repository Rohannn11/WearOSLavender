package com.example.lavender.presentation

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    private val _locationState = MutableStateFlow("Fetching location...")
    val locationState: StateFlow<String> = _locationState

    private val _isTracking = MutableStateFlow(false)
    val isTracking: StateFlow<Boolean> = _isTracking

    @SuppressLint("MissingPermission")
    fun initialize(fusedLocationClient: FusedLocationProviderClient) {
        this.fusedLocationClient = fusedLocationClient
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
            .setMinUpdateIntervalMillis(1000)
            .setMaxUpdateDelayMillis(5000)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    _locationState.value = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                    if (_isTracking.value) {
                        saveLocationUpdate(location)
                    }
                }
            }
        }

        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback!!, null)
    }

    private fun saveLocationUpdate(location: Location) {
        viewModelScope.launch {
            // Implement location saving to Room database or remote server
            Log.d("LocationTracking", "Location saved: ${location.latitude}, ${location.longitude}")
        }
    }

    fun toggleTracking() {
        _isTracking.value = !_isTracking.value
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }
}
