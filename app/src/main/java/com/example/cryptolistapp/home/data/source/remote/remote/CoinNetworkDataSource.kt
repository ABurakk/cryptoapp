package com.example.cryptolistapp.home.data.source.remote.remote

import com.example.cryptolistapp.data.source.remote.CoinsApiModel
import retrofit2.Response

interface CoinNetworkDataSource {
    suspend fun getCoins(
    ): Response<CoinsApiModel>
}