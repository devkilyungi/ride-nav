package com.example.ridenav.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ridenav.data.dto.Driver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

@Composable
fun AccountScreen(
    onSettingsClick: () -> Unit,
    onSignOut: () -> Unit
) {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var vehicleType by remember { mutableStateOf("") }
    var licencePlate by remember { mutableStateOf("") }

    var auth = FirebaseAuth.getInstance()

    val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbUsers: CollectionReference = dB.collection("drivers")

    dbUsers.document("${auth.currentUser?.uid}")
        .get()
        .addOnSuccessListener { userSnapshot ->
            if (userSnapshot != null) {

                val userData = userSnapshot.toObject<Driver>()

                fullName = "${userData!!.firstName} ${userData.lastName}"
                email = userData!!.email
                vehicleType = userData.vehicleType
                licencePlate = userData.licencePlate

                Log.d("TAG", "DocumentSnapshot data: ${userSnapshot.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d("TAG", "Error getting document: ", exception)
        }



    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "Account",
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Full Name:",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = fullName,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Email:",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Vehicle Type:",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = vehicleType,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Licence Plate:",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = licencePlate,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}