package com.example.olyaandroid.ui.screens

// Importing necessary Compose UI elements
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// This is a Composable function that shows a screen where the user can type and save a comment
@Composable
fun CommentScreen(
    onMyWeekClick: () -> Unit, // Function that will be called when "My Week" button is clicked
    commentViewModel: CommentViewModel = viewModel() // Gets the ViewModel for managing the comment
) {
    val comment = commentViewModel.comment.value // Gets the current comment text from the ViewModel

    // Arranges everything in a column layout, centered both vertically and horizontally
    Column(
        modifier = Modifier
            .fillMaxSize() // Takes the full size of the screen
            .padding(16.dp), // Adds padding around the edges
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // A text field where the user can type a comment
        OutlinedTextField(
            value = comment, // Shows the current comment value
            onValueChange = { commentViewModel.comment.value = it }, // Updates the comment in ViewModel when user types
            placeholder = { Text("Write something about your day…") }, // Shows this text when the field is empty
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Button to save the comment
        Button(
            onClick = { commentViewModel.saveEntry() }, // Calls saveEntry() when clicked
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = comment.isNotBlank() // Only enabled if the comment is not empty
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(24.dp)) // Adds space between the two buttons

        // Button to navigate to the "My Week" screen
        Button(
            onClick = onMyWeekClick, // Calls the function passed in when clicked
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("My Week")
        }
    }
}
