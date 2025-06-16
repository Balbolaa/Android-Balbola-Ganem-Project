// HomeScreen.kt
package com.example.olyaandroid.ui.screens

// Importing Compose and Material3 UI components
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import java.text.SimpleDateFormat
import java.util.Date

// Marking this as experimental since DatePicker is still experimental in Material3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onHowWeFeelingClick: () -> Unit, // Function to go to mood screen
    onQuoteClick: () -> Unit,        // Function to go to quote/inspiration screen
    homeViewModel: HomeViewModel = viewModel(), // ViewModel that holds the selected date
    navController: NavController // Used to navigate between screens
) {
    var showDatePicker by remember { mutableStateOf(false) } // Controls if the date picker is visible
    val selectedDate = homeViewModel.selectedDate.value      // Get selected date from ViewModel

    // Main layout for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Top bar with title and maybe profile icon
        TopBarWithProfile(title = "Home", navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        // App name title
        Text(
            text = "MyMood",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Show the selected date if it's already picked
        selectedDate?.let { date ->
            Text(
                text = "Selected Date: $date",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Button to open the date picker dialog
        Button(
            onClick = { showDatePicker = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Pick a Date")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Button to go to mood screen, only enabled if a date is selected
        Button(
            onClick = onHowWeFeelingClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = selectedDate != null
        ) {
            Text("How we feeling ?")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Button to go to daily quote screen
        Button(
            onClick = onQuoteClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Daily Inspiration")
        }

        // Show the date picker dialog if user clicked "Pick a Date"
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState() // Keeps track of selected date
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false }, // Close dialog if dismissed
                confirmButton = {
                    TextButton(
                        onClick = {
                            // Convert selected millis to formatted date
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = SimpleDateFormat("yyyy-MM-dd").format(Date(millis))
                                homeViewModel.selectedDate.value = date
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState) // The actual calendar UI
            }
        }
    }
}
