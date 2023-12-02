package com.example.ridenav.presentation.screens.sign_up

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ridenav.common.isLicensePlateValid
import com.example.ridenav.data.dto.Driver
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsScreenViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    // Input fields text states
    var firstName by mutableStateOf(TextFieldValue(""))
    var lastName by mutableStateOf(TextFieldValue(""))
    var vehicleType by mutableStateOf(TextFieldValue(""))
    var licencePlate by mutableStateOf(TextFieldValue(""))

    // Input fields error state
    var firstNameError by mutableStateOf(false)
    var firstNameErrorMessage by mutableStateOf("")
    var lastNameError by mutableStateOf(false)
    var lastNameErrorMessage by mutableStateOf("")
    var vehicleTypeError by mutableStateOf(false)
    var vehicleTypeErrorMessage by mutableStateOf("")
    var licencePlateError by mutableStateOf(false)
    var licencePlateErrorMessage by mutableStateOf("")

    // Continue button state
    var buttonText by mutableStateOf("Finish")
    var isLoading = false

    // Notice visibility state
    var noticeVisible by mutableStateOf(false)
    var informationNotice by mutableStateOf(false)
    var informationNoticeContent by mutableStateOf("")
    private var informationNoticeButtonText by mutableStateOf("Close")

    // Form validation
    private fun isValidForm(): Boolean {
        when {
            licencePlate.text.isEmpty() -> {
                licencePlateError = true
                licencePlateErrorMessage = "Enter your vehicle's licence plate"
            }

            !licencePlate.text.trim().isLicensePlateValid() -> {
                licencePlateError = true
                licencePlateErrorMessage = "Enter a valid licence plate"
            }

            else -> licencePlateError = false

        }

        if (firstName.text.isEmpty()) {
            firstNameError = true
            firstNameErrorMessage = "Enter your first name"
        } else firstNameError = false

        if (lastName.text.isEmpty()) {
            lastNameError = true
            lastNameErrorMessage = "Enter your last name"
        } else lastNameError = false

        if (vehicleType.text.isEmpty()) {
            vehicleTypeError = true
            vehicleTypeErrorMessage = "Enter your vehicle type"
        } else vehicleTypeError = false

        return !firstNameError && !lastNameError && !vehicleTypeError && !licencePlateError
    }

    fun addUserDetails(onFinishClick: () -> Unit) = viewModelScope.launch {

        if (!isLoading && !noticeVisible && isValidForm()) {
            val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
            val dbUsers: CollectionReference = dB.collection("drivers")

            val driver = Driver(
                firstName = firstName.text,
                lastName = lastName.text,
                vehicleType = vehicleType.text,
                licencePlate = licencePlate.text
            )

            dbUsers.add(driver)
                .addOnSuccessListener {
                    Log.d("TAG", "put details succeeded")

                    onFinishClick()

                    Toast.makeText(application, "Account details saved!", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { e ->
                    Log.d("TAG", "put details failed: ${e.message}")
                    isLoading = false
                    showInformationNotice("An error occurred: ${e.message}")
                    buttonText = "Finish"
                }
        }
    }

    private fun showInformationNotice(content: String, buttonText: String = "Close") {
        informationNoticeContent = content
        informationNoticeButtonText = buttonText
        noticeVisible = true
        informationNotice = true
    }

    // Function to close notice
    fun closeNotice() {
        noticeVisible = false
        informationNotice = false
    }
}