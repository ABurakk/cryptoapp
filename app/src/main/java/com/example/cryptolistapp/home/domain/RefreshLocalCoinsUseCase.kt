package com.example.cryptolistapp.home.domain

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.home.data.repository.CoinRepository
import com.example.cryptolistapp.home.data.source.local.model.Coin
import javax.inject.Inject

class RefreshLocalCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    suspend operator fun invoke(): Result<List<Coin>> {
        return refreshCoins()
    }

    private suspend fun refreshCoins(): Result<List<Coin>> {
        val remoteCoinsResult = coinRepository.fetchRemoteCoins()

        if (remoteCoinsResult is Result.Success) {
            coinRepository.refreshLocalCoins(remoteCoinsResult.data)
        }

        return remoteCoinsResult
    }
}
