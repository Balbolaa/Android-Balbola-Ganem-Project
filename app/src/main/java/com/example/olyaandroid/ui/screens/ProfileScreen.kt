package com.example.olyaandroid.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    // Outer Box to fill whole screen
    Box(modifier = Modifier.fillMaxSize()) {
        // Top app bar with title and back button
        TopAppBar(
            title = { Text("Profile", textAlign = TextAlign.Center) }, // Title centered
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) { // When back icon clicked
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back") // Back arrow icon
                }
            }
        )

        // Content box that fills the screen and centers its content
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("User Profile Info") // Placeholder for user profile info
        }
    }
}
