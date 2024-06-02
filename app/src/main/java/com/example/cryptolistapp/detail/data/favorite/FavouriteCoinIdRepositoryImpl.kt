package com.example.cryptolistapp.detail.data.favorite

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.home.data.repository.CoinError
import com.example.cryptolistapp.home.data.source.local.LocalCoinDataSource
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FavouriteCoinIdRepositoryImpl @Inject constructor(
    private val coinLocalDataSource: LocalCoinDataSource,
) : FavouriteCoinIdRepository {

    override fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Result<Boolean>> {
        return coinLocalDataSource.checkIfCoinIsFavourite(favouriteCoinId = favouriteCoinId)
            .map { Result.Success(it) }
            .catch { e ->
                Result.Error<Boolean>(CoinError.NETWORK_ERROR.message)
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun changeFavouriteStatus(favouriteCoinId: FavouriteCoinId) {
        withContext(Dispatchers.IO) {
            coinLocalDataSource.changeFavouriteStatus(favouriteCoinId)
        }
    }
}
