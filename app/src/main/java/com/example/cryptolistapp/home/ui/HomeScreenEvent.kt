package com.example.cryptolistapp.home.ui


sealed class HomeScreenEvent {
    object Initialise : HomeScreenEvent()
    object PullRefresh : HomeScreenEvent()
    object DismissErrorMessage : HomeScreenEvent()
}