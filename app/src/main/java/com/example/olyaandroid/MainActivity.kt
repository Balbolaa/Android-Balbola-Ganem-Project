package com.example.olyaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import com.example.olyaandroid.ui.screens.*
import com.example.olyaandroid.ui.theme.OlyaAndroidTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.olyaandroid.ui.screens.HomeViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OlyaAndroidTheme {
                val navController = rememberNavController()
                val homeViewModel: HomeViewModel = viewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (/* TODO: check login state */ false) "home" else "login"
                    ) {
                        composable("login") {
                            LoginScreen(onLoginSuccess = { navController.navigate("home") { popUpTo("login") { inclusive = true } } })
                        }
                        composable("home") {
                            HomeScreen(
                                onHowWeFeelingClick = { navController.navigate("mood") },
                                onQuoteClick = { navController.navigate("quote") },
                                homeViewModel = homeViewModel,
                                navController = navController
                            )
                        }
                        composable("mood") {
                            MoodScreen(
                                onTellMeMoreClick = { navController.navigate("comment") },
                                navController = navController
                            )
                        }
                        composable("comment") {
                            CommentScreen(onMyWeekClick = { navController.navigate("stats") })
                        }
                        composable("stats") {
                            StatsScreen(onMotivationClick = { navController.navigate("quote") })
                        }
                        composable("quote") {
                            QuoteScreen(onBackClick = { navController.popBackStack() })
                        }
                        composable("profile_screen") {
                            ProfileScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
