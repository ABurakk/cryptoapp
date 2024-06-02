package com.example.cryptolistapp.detail.domain

import com.example.cryptolistapp.home.data.source.local.model.Price

data class CoinDetails(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val currentPrice: Price,
)