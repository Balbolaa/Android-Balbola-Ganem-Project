package com.example.olyaandroid.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// This data class holds the UI state for the Quote screen
data class QuoteState(
    val isLoading: Boolean = true,  // True when loading a quote
    val quote: String = ""           // The quote text
)

class QuoteViewModel : ViewModel() {
    // Backing mutable state flow, holds the current QuoteState
    private val _state = MutableStateFlow(QuoteState())

    // Public state flow to expose read-only state
    val state: StateFlow<QuoteState> = _state

    init {
        loadQuote()  // Start loading quote when ViewModel is created
    }

    // Loads a quote asynchronously (simulated with delay here)
    private fun loadQuote() {
        viewModelScope.launch {
            _state.value = QuoteState(isLoading = true) // Set loading state
            delay(2000) // Simulate network or data fetching delay
            // Update state with loaded quote and loading = false
            _state.value = QuoteState(
                isLoading = false,
                quote = "Your mental health is a priority. Your happiness is essential. Your self-care is a necessity."
            )
        }
    }
}
