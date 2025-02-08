package com.example.lavender.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var sosViewModel: SOSViewModel
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var panicModeViewModel: PanicModeViewModel // Add this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contactManager = EmergencyContactManager(this)
        val contactsViewModelFactory = ContactsViewModelFactory(contactManager)

        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        sosViewModel = ViewModelProvider(this)[SOSViewModel::class.java]
        contactsViewModel = ViewModelProvider(this, contactsViewModelFactory)[ContactsViewModel::class.java]

        panicModeViewModel = ViewModelProvider(this)[PanicModeViewModel::class.java] // Initialize it

        locationViewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        requestPermissions()

        setContent {
            WearAppScreen(
                locationViewModel = locationViewModel,
                sosViewModel = sosViewModel,
                contactsViewModel = contactsViewModel,
                panicModeViewModel = panicModeViewModel // Pass it here
            )
        }
    }

    private fun requestPermissions() {
        val requiredPermissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.SEND_SMS
        )

        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            initializeLocationTracking()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                initializeLocationTracking()
            } else {
                Toast.makeText(this, "Required permissions not granted!", Toast.LENGTH_SHORT).show()
            }
        }

    @Suppress("MissingPermission")
    private fun initializeLocationTracking() {
        locationViewModel.initialize(locationViewModel.fusedLocationClient!!)
    }
}
