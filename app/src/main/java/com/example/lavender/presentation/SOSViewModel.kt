package com.example.lavender.presentation
import android.telephony.SmsManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SOSViewModel : ViewModel() {
    private val _sosState = MutableStateFlow<SOSState>(SOSState.Idle)
    val sosState: StateFlow<SOSState> = _sosState

    private val emergencyContacts = listOf(
        EmergencyContact("Parent 1", "+1234567890"),
        EmergencyContact("Parent 2", "+0987654321"),
        EmergencyContact("Emergency Contact", "+1122334455")
    )

    sealed class SOSState {
        data object Idle : SOSState()
        data class Confirming(val remainingSeconds: Int) : SOSState()
        data object Sending : SOSState()
        data object Sent : SOSState()
        data class Error(val message: String) : SOSState()
    }

    fun initiateSOSSequence(currentLocation: String) {
        viewModelScope.launch {
            try {
                // Start 5-second countdown
                for (i in 5 downTo 1) {
                    _sosState.value = SOSState.Confirming(i)
                    delay(1000)
                }

                if (_sosState.value is SOSState.Confirming) {
                    _sosState.value = SOSState.Sending
                    sendSOSMessages(currentLocation)
                }
            } catch (e: Exception) {
                _sosState.value = SOSState.Error("SOS sequence failed: ${e.message}")
            }
        }
    }

    private suspend fun sendSOSMessages(location: String) {
        try {
            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            val message = "EMERGENCY SOS ALERT!\n" +
                    "Time: $timestamp\n" +
                    "Location: $location\n" +
                    "This is an emergency alert. Please contact immediately."

            emergencyContacts.forEach { contact ->
                // In production, uncomment this line and handle permissions
                // SmsManager.getDefault().sendTextMessage(contact.phoneNumber, null, message, null, null)
            }

            _sosState.value = SOSState.Sent
            delay(3000) // Show "Sent" state for 3 seconds
            _sosState.value = SOSState.Idle
        } catch (e: Exception) {
            _sosState.value = SOSState.Error("Failed to send SOS messages: ${e.message}")
        }
    }

    fun cancelSOS() {
        _sosState.value = SOSState.Idle
    }
}

data class EmergencyContact(
    val name: String,
    val phoneNumber: String
)
