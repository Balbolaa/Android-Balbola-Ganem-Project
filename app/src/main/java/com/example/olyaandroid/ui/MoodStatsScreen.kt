package com.example.olyaandroid.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MoodStatsScreen(
    viewModel: MoodStatsViewModel = viewModel()
) {
    // Collect the current UI state from the ViewModel as a Compose state
    val uiState by viewModel.uiState.collectAsState()
    val primaryColor = MaterialTheme.colorScheme.primary
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .fillMaxSize()       // Take full available screen size
            .padding(16.dp),     // Add padding around the content
        horizontalAlignment = Alignment.CenterHorizontally // Center contents horizontally
    ) {
        // Title text at the top of the screen
        Text(
            text = "Mood Statistics",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)  // Space below the title
        )

        // Container for the bar chart with fixed height
        Box(
            modifier = Modifier
                .fillMaxWidth()    // Bar chart fills width of the screen
                .height(300.dp)    // Fixed height for chart
                .padding(16.dp)    // Padding inside the box
        ) {
            // Canvas used for drawing custom graphics like the bar chart
            Canvas(modifier = Modifier.fillMaxSize()) {
                val barWidth = size.width / 7           // Divide width equally for 7 bars (7 days)
                val maxMood = 5f                        // Max mood level to scale bars
                val scale = size.height / maxMood       // Scale factor to convert mood level to pixel height

                // Loop through each mood entry to draw bars
                uiState.moodEntries.forEachIndexed { index, entry ->
                    val barHeight = entry.mood * scale  // Height of the bar based on mood level
                    val x = index * barWidth             // X position for each bar
                    val y = size.height - barHeight      // Y position so bars grow upward

                    // Draw the rectangle bar itself
                    drawRect(
                        color = primaryColor,
                        topLeft = Offset(x + 8.dp.toPx(), y),       // Add small horizontal padding inside bar slot
                        size = androidx.compose.ui.geometry.Size(
                            barWidth - 16.dp.toPx(),                 // Width smaller than slot width for spacing
                            barHeight
                        )
                    )

                    // Draw the date text below each bar using native Android canvas API
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            entry.date.substring(5), // Show only MM-DD part of date
                            x + barWidth / 2,        // Center text horizontally under the bar
                            size.height + 20.dp.toPx(),  // Position text slightly below the bar chart
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = with(density) { 12.sp.toPx() } // Set text size with density scaling
                                textAlign = android.graphics.Paint.Align.CENTER  // Center align text
                            }
                        )
                    }
                }
            }
        }

        // Card showing average mood below the chart
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),   // Space between chart and card
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally  // Center content inside card
            ) {
                Text(
                    text = "Average Mood",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = String.format(Locale.getDefault(), "%.1f", uiState.averageMood), // Show avg with 1 decimal
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
