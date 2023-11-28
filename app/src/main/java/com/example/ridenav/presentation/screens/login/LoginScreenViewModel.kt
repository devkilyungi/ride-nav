package com.example.ridenav.presentation.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ridenav.common.Resource
import com.example.ridenav.common.isValidEmail
import com.example.ridenav.common.navigateOnce
import com.example.ridenav.domain.repository.AuthRepository
import com.example.ridenav.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // Email and password fields text states
    var email by mutableStateOf(TextFieldValue(""))
    var password by mutableStateOf(TextFieldValue(""))

    // Password visibility state
    var passwordVisibility by mutableStateOf(false)

    // Email error state
    var emailError by mutableStateOf(false)
    var emailErrorMessage by mutableStateOf("")

    // Password error state
    var passwordError by mutableStateOf(false)
    var passwordErrorMessage by mutableStateOf("")

    // Login button state
    var buttonText by mutableStateOf("Sign in")
    private var loginAnimationJob: Job? = null
    var isLoading = false

    // Notice visibility state
    var noticeVisible by mutableStateOf(false)
    var informationNotice by mutableStateOf(false)

    var informationNoticeContent by mutableStateOf("")
    private var informationNoticeButtonText by mutableStateOf("Close")

    // Password visibility changed handler
    fun onPasswordVisibilityChanged(newVisibilityState: Boolean) {
        passwordVisibility = newVisibilityState
    }

    // Form validation
    private fun isValidForm(): Boolean {
        when {
            email.text.isEmpty() -> {
                emailError = true
                emailErrorMessage = "Enter your email address"
            }

            !email.text.trim().isValidEmail() -> {
                emailError = true
                emailErrorMessage = "Enter a valid email address"
            }

            else -> emailError = false

        }
        if (password.text.isEmpty()) {
            passwordError = true
            passwordErrorMessage = "Enter password"
        } else passwordError = false
        return !emailError && !passwordError
    }

    fun loginUser(navController: NavController) = viewModelScope.launch {

        if (!isLoading && !noticeVisible && isValidForm()) {
            authRepository.loginUser(email = email.text, password = password.text)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            isLoading = true
                            loginAnimationJob = viewModelScope.launch {
                                buttonText = "Loading"
                                delay(3000)
                                buttonText = "Hang on"
                                delay(3000)
                                buttonText = "Just a sec"
                                delay(4000)
                                buttonText = "Something's not right"
                                delay(1000)
                                buttonText = "Retrying"
                            }
                        }

                        is Resource.Success -> {
                            isLoading = false
                            loginAnimationJob?.cancelAndJoin()
                            buttonText = "Sign in"

                            navController.apply {
                                popBackStack()
                                navigateOnce(Screen.HomeScreen.route)
                            }
                        }

                        is Resource.Error -> {
                            isLoading = false
                            showInformationNotice("An error occurred: ${result.message}")
                            loginAnimationJob?.cancelAndJoin()
                            buttonText = "Sign in"
                            Log.d("TAG", "signIn: ${result.message}")
                        }
                    }
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

