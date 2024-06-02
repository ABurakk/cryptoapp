package com.example.cryptolistapp.detail.ui

sealed class DetailsEvent {
    data class ScreenOpened(val id: String) : DetailsEvent()
    data class ShowError(val errorMessage: String) : DetailsEvent()
    object ToggleIsCoinFavourite : DetailsEvent()
}