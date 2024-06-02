package com.example.cryptolistapp.home.data.source.remote.remote

import com.example.cryptolistapp.data.source.remote.CoinsApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApi {
    @GET("coins")
    suspend fun getCoins(
        @Query("referenceCurrencyUuid") currencyUUID: String = "yhjMzLPhuIDl",
        @Query("orderBy") orderBy: String = "marketCap",
        @Query("timePeriod") timePeriod: String = "24h",
        @Query("orderDirection") orderDirection: String = "desc",
        @Query("limit") limit: String = "100"
    ): Response<CoinsApiModel>
}