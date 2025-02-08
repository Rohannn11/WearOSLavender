package com.example.lavender.presentation

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocationViewModel : ViewModel() {
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    private val _locationState = MutableStateFlow("Fetching location...")
    val locationState: StateFlow<String> = _locationState

    private val _isTracking = MutableStateFlow(false)
    val isTracking: StateFlow<Boolean> = _isTracking

    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
        .setMinUpdateIntervalMillis(1000)
        .setMaxUpdateDelayMillis(5000)
        .build()

    @SuppressLint("MissingPermission")
    fun initialize(fusedLocationClient: FusedLocationProviderClient) {
        this.fusedLocationClient = fusedLocationClient
        setupLocationCallback()
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    _locationState.value = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun toggleTracking() {
        _isTracking.value = !_isTracking.value
        if (_isTracking.value) {
            fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback!!, null)
            Log.d("LocationTracking", "Tracking started")
        } else {
            fusedLocationClient?.removeLocationUpdates(locationCallback!!)
            Log.d("LocationTracking", "Tracking stopped")
        }
    }

    override fun onCleared() {
        super.onCleared()
        fusedLocationClient?.removeLocationUpdates(locationCallback!!)
    }
}
