package com.example.cryptolistapp.detail.data.detail

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.detail.domain.CoinDetails
import com.example.cryptolistapp.home.data.repository.CoinError
import com.example.cryptolistapp.home.data.source.remote.remote.CoinNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CoinDetailsRepositoryImpl @Inject constructor(
    private val coinNetworkDataSource: CoinNetworkDataSource,
    private val coinDetailsMapper: CoinDetailsMapper,
) : CoinDetailsRepository {
    override fun getCoinDetails(coinId: String): Flow<Result<CoinDetails>> =
        flow {
            val response = coinNetworkDataSource.getCoinDetails(
                coinId = coinId,
            )

            val body = response.body()

            if (response.isSuccessful && body?.coinDetailsDataHolder?.coinDetailsData != null) {
                val coinDetails = coinDetailsMapper.mapApiModelToModel(body)
                emit(Result.Success(coinDetails))
            } else {
                emit(Result.Error(CoinError.NETWORK_ERROR.message))
            }
        }.catch { e ->
            emit(Result.Error(CoinError.NETWORK_ERROR.message))
        }.flowOn(Dispatchers.IO)
}
