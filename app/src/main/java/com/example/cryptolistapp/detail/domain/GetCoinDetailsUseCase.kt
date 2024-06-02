package com.example.cryptolistapp.detail.domain

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.detail.data.detail.CoinDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val coinDetailsRepository: CoinDetailsRepository,
) {
    operator fun invoke(coinId: String): Flow<Result<CoinDetails>> {
        return getCoinDetails(coinId = coinId)
    }

    private fun getCoinDetails(coinId: String): Flow<Result<CoinDetails>> {
        return coinDetailsRepository.getCoinDetails(
            coinId = coinId,
        )
    }
}
