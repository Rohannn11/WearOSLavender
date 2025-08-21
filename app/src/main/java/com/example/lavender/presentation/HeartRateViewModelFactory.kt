package com.example.lavender.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HeartRateViewModelFactory(
    private val application: Application,
    private val sosViewModel: SOSViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeartRateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeartRateViewModel(application, sosViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}