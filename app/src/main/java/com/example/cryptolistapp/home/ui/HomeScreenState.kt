package com.example.cryptolistapp.home.ui

import androidx.compose.runtime.Immutable
import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.domain.CoinSort

@Immutable
data class HomeScreenState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val coinSort: CoinSort = CoinSort.MarketCap,
    val errorMessage: String? = null,
    val marketCapChangePercentage24h: Float = 0f
)