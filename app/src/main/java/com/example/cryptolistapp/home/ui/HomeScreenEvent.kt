package com.example.cryptolistapp.home.ui

import com.example.cryptolistapp.home.domain.CoinSort


sealed class HomeScreenEvent {
    data class SortCoins(val coinSort: CoinSort) : HomeScreenEvent()
    data object ScreenOpened : HomeScreenEvent()
    data object PullRefresh : HomeScreenEvent()
    data object DismissErrorMessage : HomeScreenEvent()
}