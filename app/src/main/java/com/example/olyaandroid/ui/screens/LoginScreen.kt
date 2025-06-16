package com.example.olyaandroid.ui.screens

// Importing Compose UI components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

// This Composable function shows the login screen
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit // This function is called when the user logs in
) {
    // These variables hold the current input for email and password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Main column layout for the screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Use the full screen size
            .padding(16.dp), // Add padding around the content
        horizontalAlignment = Alignment.CenterHorizontally, // Center items horizontally
        verticalArrangement = Arrangement.Center // Center items vertically
    ) {
        // App welcome text
        Text(
            text = "Welcome to MyMood",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Text field for entering email
        OutlinedTextField(
            value = email, // Shows the current email input
            onValueChange = { email = it }, // Updates the email when the user types
            label = { Text("Email") }, // Label shown inside the text field
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, // Brings up an email-friendly keyboard
                imeAction = ImeAction.Next // "Next" button on keyboard
            )
        )

        // Text field for entering password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            visualTransformation = PasswordVisualTransformation(), // Hides password input
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        // Login button
        Button(
            onClick = { onLoginSuccess() }, // Calls the login success function (navigation or something else)
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Login")
        }
    }
}
