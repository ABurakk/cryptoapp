package com.example.cryptolistapp.home.ui

import androidx.annotation.StringRes

sealed class HomeScreenEvent {
    object Initialise : HomeScreenEvent()
    object PullRefresh : HomeScreenEvent()
    data class DismissErrorMessage(@StringRes val dismissedErrorMessageId: Int) : HomeScreenEvent()
}