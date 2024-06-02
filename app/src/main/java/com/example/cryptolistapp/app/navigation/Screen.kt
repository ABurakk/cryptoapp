package com.example.cryptolistapp.app.navigation

sealed class Screen(val route: String) {
    data object Details : Screen("details_screen")
    data object Home : Screen("navigation_bar_screen")
}
