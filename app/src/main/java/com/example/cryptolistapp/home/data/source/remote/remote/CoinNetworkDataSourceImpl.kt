package com.example.cryptolistapp.home.data.source.remote.remote

import com.example.cryptolistapp.data.source.remote.CoinsApiModel
import retrofit2.Response
import javax.inject.Inject

class CoinNetworkDataSourceImpl @Inject constructor(
    private val coinApi: CoinApi
) : CoinNetworkDataSource {
    override suspend fun getCoins(): Response<CoinsApiModel> {
        return coinApi.getCoins()
    }
}