package com.example.ridenav.presentation.screens.login

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ridenav.presentation.navigation.Screen
import com.example.ridenav.presentation.screens.login.components.TextInput

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = hiltViewModel(),
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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Sign in header
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sign in",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Welcome text
                Text(
                    text = "Welcome back",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                    onDone = { viewModel.loginUser(navController) },
                    isPassword = true,
                    passwordVisibility = viewModel.passwordVisibility,
                    onPasswordVisibilityChanged = {
                        viewModel.onPasswordVisibilityChanged(
                            it
                        )
                    }
                )

                // Remember me section
                var switchState by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Remember me")

                    Switch(checked = switchState, onCheckedChange = {
                        switchState = it
                    })
                }

                // Sign in button
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.loginUser(navController)
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

                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )

                // Sign up link
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Don't have an account?")

                    TextButton(onClick = { navController.navigate(Screen.SignUpScreen.route) }) {
                        Text(text = "Sign up")
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