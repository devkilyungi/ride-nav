package com.example.ridenav.presentation.screens.login.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/***
 * This is a custom text field that returns either a default outline text field
 * or a password text field.
 */
@ExperimentalAnimationApi
@Composable
fun TextInput(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    onTextChanged: (TextFieldValue) -> Unit,
    onDone: () -> Unit = { },
    focusManager: FocusManager,
    keyboardOptions: KeyboardOptions,
    enabled: Boolean = true,
    isError: Boolean,
    label: String,
    singleLine: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    errorMessage: String,
    isPassword: Boolean = false,
    passwordVisibility: Boolean = false,
    onPasswordVisibilityChanged: (Boolean) -> Unit = {},
) {
    Column {
        /***
         * Check which text input is to be displayed
         */
        if (isPassword) {
            /***
             * Password text field.
             * This text field is displayed when isPassword attribute is true.
             */
            OutlinedTextField(
                value = value,
                onValueChange = {
                    onTextChanged(it)
                },
                isError = isError,
                enabled = enabled,
                label = { Text(text = label) },
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onDone()
                    }
                ),
                shape = shape,
                singleLine = singleLine,
                visualTransformation = if (passwordVisibility)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    /***
                     * Checking which icon is to be displayed depending on the current state
                     */
                    val image = if (passwordVisibility)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        onPasswordVisibilityChanged(!passwordVisibility)
                    }) {
                        Icon(imageVector = image, contentDescription = "Visibility icon")
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )
        }
        /***
         * Normal text field.
         * This text field is displayed when the isPassword attribute is false.
         */
        else OutlinedTextField(
            value = value,
            onValueChange = {
                onTextChanged(it)
            },
            enabled = enabled,
            isError = isError,
            label = { Text(text = label) },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            shape = shape,
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(2.dp),
        )
        /***
         * An error text that is displayed when the isError attribute is true
         */
        AnimatedVisibility(isError, modifier = Modifier.align(Alignment.Start)) {
            AnimatedContent(errorMessage, label = "") { message ->
                Text(
                    text = message,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}