package com.example.ridenav.presentation.screens.sign_up

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ridenav.presentation.components.Notice
import com.example.ridenav.presentation.screens.login.components.TextInput

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    focusManager: FocusManager = LocalFocusManager.current,
    navController: NavController
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                // Sign up header
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sign up",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Create account text
                Text(
                    text = "Create new account",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Email text field
                TextInput(
                    value = viewModel.email,
                    label = "Email",
                    enabled = !viewModel.noticeVisible,
                    onTextChanged = { viewModel.email = it },
                    isError = viewModel.emailError,
                    errorMessage = viewModel.emailErrorMessage,
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Password text field
                TextInput(value = viewModel.password,
                    label = "Password",
                    enabled = !viewModel.noticeVisible,
                    onTextChanged = { viewModel.password = it },
                    isError = viewModel.passwordError,
                    errorMessage = viewModel.passwordErrorMessage,
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    onDone = { viewModel.registerUser(navController) },
                    isPassword = true,
                    passwordVisibility = viewModel.passwordVisibility,
                    onPasswordVisibilityChanged = {
                        viewModel.onPasswordVisibilityChanged(
                            it
                        )
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Sign up button
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.registerUser(navController)
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    AnimatedContent(viewModel.buttonText, transitionSpec = {
                        (slideInVertically(
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        ) { it } + fadeIn()).togetherWith(slideOutVertically(
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        ) { -it } + fadeOut())
                    }, label = "") { text ->
                        Text(
                            text = text,
                            color = Color.White
                        )
                    }
                    AnimatedVisibility(viewModel.isLoading) {
                        Row {
                            Spacer(modifier = Modifier.width(16.dp))
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(16.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }

    // Information notice
    Notice(visible = viewModel.informationNotice,
        header = "Notice",
        content = viewModel.informationNoticeContent,
        primaryButtonText = "Ok",
        primaryOnClick = { viewModel.closeNotice() })
}