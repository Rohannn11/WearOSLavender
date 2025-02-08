package com.example.lavender.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ContactsViewModel(private val contactManager: EmergencyContactManager) : ViewModel() {
    private val _contacts = MutableStateFlow<List<EmergencyContact>>(emptyList())
    val contacts: StateFlow<List<EmergencyContact>> = _contacts

    init {
        loadContacts()
    }

    private fun loadContacts() {
        _contacts.value = contactManager.getContacts()
    }

    fun addContact(name: String, phoneNumber: String) {
        val newContact = EmergencyContact(name, phoneNumber)
        contactManager.saveContact(newContact)
        loadContacts()
    }

    fun removeContact(contact: EmergencyContact) {
        contactManager.removeContact(contact)
        loadContacts()
    }
}