package com.example.cryptolistapp.home.data.source.remote.remote

import com.example.cryptolistapp.data.source.remote.CoinsApiModel
import com.example.cryptolistapp.detail.data.detail.CoinDetailsApiModel
import com.example.cryptolistapp.home.domain.CoinSort
import retrofit2.Response
import javax.inject.Inject

class CoinNetworkDataSourceImpl @Inject constructor(
    private val coinApi: CoinApi
) : CoinNetworkDataSource {
    override suspend fun getCoins(coinSort: CoinSort): Response<CoinsApiModel> {
        return coinApi.getCoins(
            orderBy = coinSort.getOrderBy(),
            orderDirection = coinSort.getOrderDirection()
        )
    }

    override suspend fun getCoinDetails(coinId: String): Response<CoinDetailsApiModel> {
        return coinApi.getCoinDetails(
            coinId = coinId,
        )
    }
}

private fun CoinSort.getOrderBy(): String {
    return when (this) {
        CoinSort.MarketCap -> "marketCap"
        CoinSort.Popular -> "24hVolume"
        CoinSort.Gainers -> "change"
        CoinSort.Losers -> "change"
        CoinSort.Newest -> "listedAt"
    }
}

private fun CoinSort.getOrderDirection(): String {
    return when (this) {
        CoinSort.MarketCap -> "desc"
        CoinSort.Popular -> "desc"
        CoinSort.Gainers -> "desc"
        CoinSort.Losers -> "asc"
        CoinSort.Newest -> "desc"
    }
}

