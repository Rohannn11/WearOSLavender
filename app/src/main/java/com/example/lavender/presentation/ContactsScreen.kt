package com.example.lavender.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.wear.compose.material.*

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel,
    onBack: () -> Unit
) {
    var showAddContact by remember { mutableStateOf(false) }
    val contacts by viewModel.contacts.collectAsState()

    // **Pre-Written Contact**
    val preWrittenContacts = listOf(
        EmergencyContact("Dr. Aisha Patel", "+1 555-678-1234"),
        EmergencyContact("John Doe", "+91 98765 43210"),
        EmergencyContact("Emma Watson", "+44 7456 789012")
    )
    val randomContact = remember { preWrittenContacts.random() } // Displayed on startup

    if (showAddContact) {
        AddContactDialog(
            onDismiss = { showAddContact = false },
            onAdd = { name, phone ->
                viewModel.addContact(name, phone)
                showAddContact = false
            }
        )
    }

    Scaffold(
        timeText = { TimeText() }
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.Black)
                    }

                    Button(
                        onClick = { showAddContact = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.Add, "Add Contact", tint = Color.Black)
                    }
                }
            }

            // **Pre-Written Contact Card**
            item {
                ContactCard(
                    contact = randomContact,
                    onDelete = {}
                )
            }

            // **Display Contacts**
            if (contacts.isEmpty()) {
                item {
                    Text(
                        text = "No emergency contacts added",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            } else {
                items(contacts.take(2)) { contact ->
                    ContactCard(
                        contact = contact,
                        onDelete = { viewModel.removeContact(contact) }
                    )
                }
            }
        }
    }
}



@Composable
fun ContactCard(
    contact: EmergencyContact,
    onDelete: () -> Unit
) {
    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(Color.DarkGray),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(contact.name, color = Color.White, fontSize = 16.sp)
                Text(contact.phoneNumber, color = Color.Cyan, fontSize = 14.sp)
            }
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Default.Delete, "Delete contact", tint = Color.White)
            }
        }
    }
}



@Composable
fun AddContactDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, phone: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFF1E1E1E)), // Dark Theme
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add Emergency Contact",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                // **Stylized Name Input**
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("👤 Name", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                // **Stylized Phone Input**
                TextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // **Buttons**
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                    ) {
                        Text("Cancel", color = Color.White)
                    }

                    // **Submit Contact Button**
                    Button(
                        onClick = {
                            if (name.isNotBlank() && phone.isNotBlank()) {
                                onAdd(name, phone)
                                onDismiss() // Close dialog after adding contact
                            }
                        },
                        enabled = name.isNotBlank() && phone.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    ) {
                        Text("Submit", color = Color.Black)
                    }
                }
            }
        }
    }
}
