package com.example.cryptolistapp.app

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptolistapp.app.navigation.Screen
import com.example.cryptolistapp.home.ui.HomePage

@SuppressLint("ComposeModifierMissing")
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Screen.Home.route) {
            HomePage(
                onNavigateDetails = {
                    navController.navigate(Screen.Details.route + "/$it")
                },
            )
        }
        composable(
            route = Screen.Details.route + "/{coinId}",
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ) {
            Text(text = "Detail")
        }
    }
}
