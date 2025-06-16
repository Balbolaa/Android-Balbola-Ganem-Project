package com.example.olyaandroid.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.roundToInt

@Composable
fun StatsScreen(
    onMotivationClick: () -> Unit,                // Callback when "Get Motivation" button is clicked
    statsViewModel: StatsViewModel = viewModel() // Get ViewModel instance to access mood data
) {
    // Take the last 7 mood entries to display weekly stats
    val history = statsViewModel.moodHistory.takeLast(7)

    val maxMood = 5            // Max mood level (scale from 1 to 5)
    val barWidth = 32.dp       // Width of each bar in the bar chart
    val barSpacing = 16.dp     // Space between bars

    Box(
        modifier = Modifier
            .fillMaxSize()    // Fill entire available screen space
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)         // Outer padding around column content
                .padding(bottom = 90.dp) // Extra padding at bottom for the button area
            ,
            horizontalAlignment = Alignment.CenterHorizontally, // Center items horizontally
            verticalArrangement = Arrangement.Center           // Center items vertically
        ) {
            // Title for the screen
            Text(
                text = "My Mood This Week",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Define colors once to use inside Canvas for drawing bars, lines, points, and grid
            val primaryColor = MaterialTheme.colorScheme.primary
            val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer
            val barColor = primaryColor.copy(alpha = 0.3f)    // Light transparent color for bar fill
            val barBorderColor = primaryColor                 // Solid color for bar outline
            val pointColor = primaryColor                      // Color for points on line graph
            val gridColor = Color.Gray.copy(alpha = 0.2f)     // Light gray for background grid lines

            // Box for the bar chart and line graph visualization
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)      // Fixed height for the graph area
                    .padding(bottom = 32.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Get the Canvas width and height in pixels
                    val width = size.width
                    val height = size.height

                    val barWidthPx = barWidth.toPx()   // Convert dp to pixels for bar width
                    val spacingPx = barSpacing.toPx()  // Convert dp to pixels for spacing

                    // Calculate total width of all bars plus spacing (7 bars total)
                    val totalWidth = (barWidthPx + spacingPx) * 7

                    // Start X coordinate to center the bars horizontally in the canvas
                    val startX = (width - totalWidth) / 2

                    // Draw horizontal grid lines as background reference (4 lines)
                    for (i in 1..4) {
                        val y = height * (i / 5f)  // 20%, 40%, 60%, 80% height lines
                        drawLine(
                            color = gridColor,
                            start = Offset(0f, y),
                            end = Offset(width, y),
                            strokeWidth = 1f
                        )
                    }

                    val points = mutableListOf<Offset>()  // List to hold points for line graph

                    // Loop through each mood entry and draw bars
                    history.forEachIndexed { index, entry ->
                        // Calculate the x coordinate for the bar center
                        val x = startX + (barWidthPx + spacingPx) * index + barWidthPx / 2

                        // Calculate bar height proportional to mood level (scaled to canvas height)
                        val barHeight = (entry.moodLevel / maxMood.toFloat()) * height

                        // Calculate top y coordinate of the bar (0 at top, so subtract height)
                        val y = height - barHeight

                        // Draw the bar fill rectangle
                        drawRect(
                            color = barColor,
                            topLeft = Offset(x - barWidthPx / 2, y),
                            size = androidx.compose.ui.geometry.Size(barWidthPx, barHeight)
                        )

                        // Draw the bar border outline
                        drawRect(
                            color = barBorderColor,
                            topLeft = Offset(x - barWidthPx / 2, y),
                            size = androidx.compose.ui.geometry.Size(barWidthPx, barHeight),
                            style = Stroke(width = 2f)
                        )

                        // Save the top center of the bar as a point for the line graph
                        points.add(Offset(x, y))
                    }

                    // If there are multiple points, draw a line graph connecting them
                    if (points.size > 1) {
                        val path = Path()
                        path.moveTo(points[0].x, points[0].y)  // Start at first point
                        for (i in 1 until points.size) {
                            path.lineTo(points[i].x, points[i].y) // Draw line to next points
                        }

                        // Draw the line graph path with stroke
                        drawPath(
                            path = path,
                            color = primaryColor,
                            style = Stroke(width = 3f)
                        )

                        // Draw circles on each point to highlight mood values
                        points.forEach { point ->
                            drawCircle(
                                color = pointColor,
                                radius = 6f,
                                center = point
                            )
                        }
                    }
                }
            }

            // Row to display mood emojis and corresponding dates below the chart
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, // Space evenly across width
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // For each mood entry, show emoji and date
                history.forEach { entry ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = entry.moodEmoji,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = entry.date.substring(5), // Display only MM-dd from date string
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))  // Space between emoji row and average card

            // Calculate and display average mood for last 7 days
            val avg = statsViewModel.averageMoodLast7Days()
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer // Background color of card
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Average Mood",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = if (avg > 0) String.format("%.1f", avg) else "-",  // Show average or dash if no data
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "out of 5",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Button fixed at the bottom center to trigger motivation screen
        Button(
            onClick = onMotivationClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)   // Align button at the bottom center of the screen
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp)
        ) {
            Text("Get Motivation")
        }
    }
}
