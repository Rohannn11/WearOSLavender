package com.example.lavender.presentation

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    private val _locationState = MutableStateFlow("Fetching location...")
    val locationState = _locationState.asStateFlow()

    fun initialize(fusedLocationClient: FusedLocationProviderClient) {
        this.fusedLocationClient = fusedLocationClient
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .setMinUpdateIntervalMillis(2000)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    _locationState.value = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                    Log.d("LocationDebug", "Updated Location: ${_locationState.value}")
                }
            }
        }

        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback!!, null)
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }
}
