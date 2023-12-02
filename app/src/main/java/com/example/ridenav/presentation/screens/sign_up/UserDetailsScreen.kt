package com.example.ridenav.presentation.screens.sign_up

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ridenav.presentation.components.Notice
import com.example.ridenav.presentation.screens.login.components.TextInput

class UserDetailsScreen {
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsScreenViewModel = hiltViewModel(),
    focusManager: FocusManager = LocalFocusManager.current,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit
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

                // header
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = onBackClick) {
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
                            text = "Welcome",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Create account text
                Text(
                    text = "Set up your account",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // First name text field
                TextInput(
                    value = viewModel.firstName,
                    label = "First Name",
                    enabled = !viewModel.noticeVisible,
                    onTextChanged = { viewModel.firstName = it },
                    isError = viewModel.firstNameError,
                    errorMessage = viewModel.firstNameErrorMessage,
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Last name text field
                TextInput(
                    value = viewModel.lastName,
                    label = "Last Name",
                    enabled = !viewModel.noticeVisible,
                    onTextChanged = { viewModel.lastName = it },
                    isError = viewModel.lastNameError,
                    errorMessage = viewModel.lastNameErrorMessage,
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Vehicle type text field
                TextInput(
                    value = viewModel.vehicleType,
                    label = "Vehicle Type",
                    enabled = !viewModel.noticeVisible,
                    onTextChanged = { viewModel.vehicleType = it },
                    isError = viewModel.vehicleTypeError,
                    errorMessage = viewModel.vehicleTypeErrorMessage,
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Licence plate text field
                TextInput(
                    value = viewModel.licencePlate,
                    label = "Licence Plate",
                    enabled = !viewModel.noticeVisible,
                    onTextChanged = { viewModel.licencePlate = it },
                    isError = viewModel.licencePlateError,
                    errorMessage = viewModel.licencePlateErrorMessage,
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    onDone = { viewModel.addUserDetails(onFinishClick) }
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Finish button
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.addUserDetails(onFinishClick)
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Finish")
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