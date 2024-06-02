package com.example.cryptolistapp.detail.data.detail

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.detail.domain.CoinDetails
import kotlinx.coroutines.flow.Flow

interface CoinDetailsRepository {
    fun getCoinDetails(coinId: String): Flow<Result<CoinDetails>>
}
