package com.example.cryptolistapp.detail.ui

import com.example.cryptolistapp.detail.domain.CoinDetails

data class DetailsState(
    val coinDetails: CoinDetails? = null,
    val isCoinFavourite: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
