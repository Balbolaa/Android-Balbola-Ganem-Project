package com.example.olyaandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun MoodScreen(
    onTellMeMoreClick: () -> Unit, // Called when user clicks "Tell me more" button
    moodViewModel: MoodViewModel = viewModel(), // ViewModel for mood data
    navController: NavController // For screen navigation and TopBar
) {
    val moods = moodViewModel.moods             // List of moods to show
    val selectedMood = moodViewModel.selectedMood.value // The mood user selected

    Column(
        modifier = Modifier
            .fillMaxSize()  // Use whole screen
            .padding(16.dp), // Padding around content
        horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
        verticalArrangement = Arrangement.Top // Start from top vertically
    ) {
        TopBarWithProfile(title = "Mood", navController = navController) // Top bar with title
        Spacer(modifier = Modifier.height(16.dp)) // Space below top bar

        Text(
            text = "How do you feel today?",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Row to show mood options side by side
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp), // Space between moods
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            // For each mood in the list, show a circle with the mood text inside
            moods.forEach { mood ->
                Box(
                    modifier = Modifier
                        .size(56.dp) // Size of each mood circle
                        .clip(CircleShape) // Make circle shape
                        .background(
                            if (selectedMood == mood) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            else Color.Transparent
                        ) // Highlight background if selected
                        .border(
                            width = if (selectedMood == mood) 3.dp else 1.dp,
                            color = if (selectedMood == mood) MaterialTheme.colorScheme.primary else Color.Gray,
                            shape = CircleShape
                        ) // Thicker colored border if selected, gray otherwise
                        .clickable { moodViewModel.selectedMood.value = mood }, // Update selected mood on click
                    contentAlignment = Alignment.Center // Center the text inside the circle
                ) {
                    Text(
                        text = mood,
                        style = MaterialTheme.typography.headlineLarge // Show mood text big inside circle
                    )
                }
            }
        }

        // Text showing which mood is selected or asking user to select one
        Text(
            text = if (selectedMood != null) "You feel: $selectedMood" else "Please select your mood",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Button to continue, only enabled if user selected a mood
        Button(
            onClick = onTellMeMoreClick,
            enabled = selectedMood != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Tell me more")
        }
    }
}
