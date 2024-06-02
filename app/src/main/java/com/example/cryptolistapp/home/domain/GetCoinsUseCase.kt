package com.example.cryptolistapp.home.domain

import com.example.cryptolistapp.home.data.repository.CoinRepository
import com.example.cryptolistapp.home.data.source.local.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.cryptolistapp.common.Result

class GetCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(): Flow<Result<List<Coin>>> {
        return getCoins()
    }

    private fun getCoins(): Flow<Result<List<Coin>>> {
        return coinRepository.getCachedCoins()
    }
}
