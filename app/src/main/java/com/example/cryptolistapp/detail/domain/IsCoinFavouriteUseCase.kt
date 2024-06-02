package com.example.cryptolistapp.detail.domain

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.detail.data.favorite.FavouriteCoinIdRepository
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsCoinFavouriteUseCase @Inject constructor(
    private val favouriteCoinIdRepository: FavouriteCoinIdRepository
) {
    operator fun invoke(favouriteCoinId: FavouriteCoinId): Flow<Result<Boolean>> {
        return isCoinFavourite(favouriteCoinId = favouriteCoinId)
    }

    private fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Result<Boolean>> {
        return favouriteCoinIdRepository.isCoinFavourite(favouriteCoinId = favouriteCoinId)
    }
}
