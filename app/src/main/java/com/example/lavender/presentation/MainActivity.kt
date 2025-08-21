package com.example.lavender.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    private lateinit var locationViewModel: LocationViewModel
    private val sosViewModel: SOSViewModel by viewModels()
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var panicModeViewModel: PanicModeViewModel
    private lateinit var heartRateViewModel: HeartRateViewModel
    private lateinit var speedTrackingViewModel: SpeedTrackingViewModel

    private val requiredPermissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.SEND_SMS,
        Manifest.permission.BODY_SENSORS
    )

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                initializeLocationTracking()
            } else {
                Toast.makeText(this, "Required permissions not granted!", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize contact manager and create factories
        val contactManager = EmergencyContactManager(this)
        val contactsViewModelFactory = ContactsViewModelFactory(contactManager)
        val heartRateViewModelFactory = HeartRateViewModelFactory(application, sosViewModel)

        // Initialize ViewModels
        initializeViewModels(contactsViewModelFactory, heartRateViewModelFactory)

        // Initialize location services
        locationViewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Request necessary permissions
        requestPermissions()

        // Set up the UI
        setContent {
            WearAppScreen(
                locationViewModel = locationViewModel,
                sosViewModel = sosViewModel,
                contactsViewModel = contactsViewModel,
                panicModeViewModel = panicModeViewModel,
                heartRateViewModel = heartRateViewModel,
                speedTrackingViewModel = speedTrackingViewModel
            )
        }
    }

    private fun initializeViewModels(
        contactsViewModelFactory: ContactsViewModelFactory,
        heartRateViewModelFactory: HeartRateViewModelFactory
    ) {
        // Initialize other ViewModels
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        panicModeViewModel = ViewModelProvider(this)[PanicModeViewModel::class.java]
        contactsViewModel = ViewModelProvider(this, contactsViewModelFactory)[ContactsViewModel::class.java]
        speedTrackingViewModel = ViewModelProvider(this)[SpeedTrackingViewModel::class.java]
        heartRateViewModel = ViewModelProvider(this, heartRateViewModelFactory)[HeartRateViewModel::class.java]
    }

    private fun requestPermissions() {
        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            initializeLocationTracking()
        }
    }

    @Suppress("MissingPermission")
    private fun initializeLocationTracking() {
        locationViewModel.fusedLocationClient?.let { client ->
            locationViewModel.initialize(client)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up any resources
        locationViewModel.stopTracking()
        heartRateViewModel.resetAlertCount()
        speedTrackingViewModel.stopSpeedTracking()
    }
}
