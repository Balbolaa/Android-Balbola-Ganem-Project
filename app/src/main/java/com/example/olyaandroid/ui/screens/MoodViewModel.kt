package com.example.olyaandroid.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// This ViewModel holds the list of moods and the currently selected mood
class MoodViewModel : ViewModel() {


    val moods = listOf("😄", "😊", "😐", "😔", "😢")

    // Stores the mood the user has selected, starts as null (no mood selected)
    val selectedMood = mutableStateOf<String?>(null)
}
