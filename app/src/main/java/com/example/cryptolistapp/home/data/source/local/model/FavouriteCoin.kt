package com.example.cryptolistapp.home.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.collections.immutable.ImmutableList
import java.math.BigDecimal

@Entity
data class FavouriteCoin(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val currentPrice: Price,
    val priceChangePercentage24h: Percentage,
    val prices24h: ImmutableList<BigDecimal>
)

@Entity
data class FavouriteCoinId(
    @PrimaryKey
    val id: String
)
