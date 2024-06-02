package com.example.cryptolistapp.detail.data.favorite

import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow

interface FavouriteCoinIdRepository {
    fun isCoinFavourite(favouriteCoinId: FavouriteCoinId): Flow<Result<Boolean>>
    suspend fun changeFavouriteStatus(favouriteCoinId: FavouriteCoinId)
}