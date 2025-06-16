package com.example.olyaandroid.ui.screens

// Importing necessary classes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// This ViewModel is used to store and manage the selected date from the HomeScreen
class HomeViewModel : ViewModel() {

    // This holds the date selected by the user.
    // It starts as null (no date selected).
    val selectedDate = mutableStateOf<String?>(null)
}
