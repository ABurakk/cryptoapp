package com.example.cryptolistapp.home.ui

import androidx.compose.runtime.Immutable
import com.example.cryptolistapp.common.ui.TimeOfDay
import com.example.cryptolistapp.home.data.source.local.model.Coin

@Immutable
data class HomeScreenState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val errorMessage: String? = null,
    val marketCapChangePercentage24h: Float = 0f
)