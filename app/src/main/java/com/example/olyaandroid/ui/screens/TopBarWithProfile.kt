package com.example.olyaandroid.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithProfile(title: String, navController: NavController) {
    TopAppBar(
        // Title text displayed in the center of the app bar
        title = {
            Text(
                text = title,            // The title passed to this composable
                modifier = Modifier,     // Modifier for further customization (none here)
                textAlign = TextAlign.Center // Center the title text horizontally
            )
        },
        // Action items displayed on the right side of the app bar
        actions = {
            // Profile icon button
            IconButton(
                onClick = {
                    // When clicked, navigate to the "profile_screen"
                    navController.navigate("profile_screen")
                }
            ) {
                // The profile icon (default user account circle icon)
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
            }
        }
    )
}
