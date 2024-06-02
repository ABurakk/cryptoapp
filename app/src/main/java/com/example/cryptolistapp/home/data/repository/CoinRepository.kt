package com.example.cryptolistapp.home.data.repository

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.domain.CoinSort
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun fetchRemoteCoins(
        coinSort: CoinSort,
    ): Result<List<Coin>>

    fun getCachedCoins(): Flow<Result<List<Coin>>>
    suspend fun refreshLocalCoins(coins: List<Coin>)
}
