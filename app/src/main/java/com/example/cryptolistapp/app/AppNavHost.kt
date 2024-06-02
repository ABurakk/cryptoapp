package com.example.cryptolistapp.app

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptolistapp.app.navigation.CurrentCoinHolder
import com.example.cryptolistapp.app.navigation.Screen
import com.example.cryptolistapp.detail.ui.DetailPage
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
                    CurrentCoinHolder.currentCoinId = it
                    navController.navigate(Screen.Details.route)
                }
            )
        }
        composable(
            route = Screen.Details.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) }
        ) {
            DetailPage(onBack = { navController.popBackStack() })
        }
    }
}
