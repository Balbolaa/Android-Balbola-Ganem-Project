package com.example.olyaandroid.ui.screens

// Import needed for state and ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// This ViewModel stores and manages the comment data for the UI
class CommentViewModel : ViewModel() {

    // This holds the current text the user is typing
    val comment = mutableStateOf("")

    // This keeps track of the last saved comment
    val lastSavedComment = mutableStateOf("")

    // This function is called when the user clicks "Save"
    fun saveEntry() {
        // It stores the current comment in lastSavedComment
        lastSavedComment.value = comment.value
        // (Optional: you could also clear the comment here if you want)
        // comment.value = ""
    }
}
