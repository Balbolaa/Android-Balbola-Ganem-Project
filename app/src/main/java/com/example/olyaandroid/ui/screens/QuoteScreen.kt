package com.example.olyaandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    onBackClick: () -> Unit, // Called when back button is clicked
    viewModel: QuoteViewModel = viewModel() // ViewModel for getting quote data
) {
    // Collect the UI state from the ViewModel as Compose state
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Inspiration") }, // Screen title
                navigationIcon = {
                    IconButton(onClick = onBackClick) { // Back button action
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues -> // Scaffold content padding
        Box(
            modifier = Modifier
                .fillMaxSize() // Fill whole screen
                .padding(paddingValues) // Respect scaffold padding
                .padding(16.dp), // Extra padding inside
            contentAlignment = Alignment.Center // Center content inside box
        ) {
            if (state.isLoading) { // Show loading spinner while loading quote
                CircularProgressIndicator()
            } else { // Show the quote text when loaded
                Text(
                    text = state.quote,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
