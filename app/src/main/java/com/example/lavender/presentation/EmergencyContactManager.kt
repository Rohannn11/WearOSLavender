package com.example.lavender.presentation

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmergencyContactManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("emergency_contacts", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveContact(contact: EmergencyContact) {
        val contacts = getContacts().toMutableList()
        if (contacts.size < 2) { // Ensure only 2 contacts are stored
            contacts.add(contact)
            saveContacts(contacts)
        }
    }

    fun removeContact(contact: EmergencyContact) {
        val contacts = getContacts().toMutableList()
        contacts.remove(contact)
        saveContacts(contacts)
    }

    fun getContacts(): List<EmergencyContact> {
        val json = prefs.getString("contacts", "[]")
        val type = object : TypeToken<List<EmergencyContact>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    private fun saveContacts(contacts: List<EmergencyContact>) {
        val json = gson.toJson(contacts)
        prefs.edit().putString("contacts", json).apply()
    }
}
