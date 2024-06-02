package com.example.cryptolistapp.home.data.source.remote.remote

import com.example.cryptolistapp.data.source.remote.CoinsApiModel
import com.example.cryptolistapp.home.domain.CoinSort
import retrofit2.Response

interface CoinNetworkDataSource {
    suspend fun getCoins(
        coinSort: CoinSort,
    ): Response<CoinsApiModel>
}