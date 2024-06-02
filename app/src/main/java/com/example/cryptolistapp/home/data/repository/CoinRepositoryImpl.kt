package com.example.cryptolistapp.home.data.repository

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.home.data.mapper.CoinMapper
import com.example.cryptolistapp.home.data.source.local.LocalCoinDataSource
import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.data.source.remote.remote.CoinNetworkDataSource
import com.example.cryptolistapp.home.domain.CoinSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val localCoinDataSource: LocalCoinDataSource,
    private val coinMapper: CoinMapper,
) : CoinRepository {

    override suspend fun fetchRemoteCoins(coinSort: CoinSort): Result<List<Coin>> {
        return try {
            val response = coinNetworkDataSource.getCoins(coinSort)
            val body = response.body()
            if (response.isSuccessful && body?.coinsData != null) {
                Result.Success(coinMapper.mapApiModelToModel(body))
            } else {
                Result.Error(CoinError.NETWORK_ERROR.message)
            }
        } catch (e: Exception) {
            Result.Error(CoinError.NETWORK_ERROR.message)
        }
    }

    override fun getCachedCoins(): Flow<Result<List<Coin>>> {
        return flow {
            try {
                localCoinDataSource.fetchAllCoins()
                    .collect { coins ->
                        emit(Result.Success(coins))
                    }
            } catch (e: Exception) {
                emit(Result.Error(CoinError.CACHE_ERROR.message))
            }
        }
    }

    override suspend fun refreshLocalCoins(coins: List<Coin>) {
        localCoinDataSource.refreshCoins(coins)
    }
}

enum class CoinError(val message: String) {
    NETWORK_ERROR("Network error: Could not retrieve coin data"),
    CACHE_ERROR("Cache error: Could not access cached coins")
}
