package com.example.cryptolistapp.detail.domain

import com.example.cryptolistapp.detail.data.favorite.FavouriteCoinIdRepository
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId
import javax.inject.Inject


class ChangeCoinFavouriteStatusUseCase @Inject constructor(
    private val favouriteCoinIdRepository: FavouriteCoinIdRepository
) {
    suspend operator fun invoke(favouriteCoinId: FavouriteCoinId) {
        return changeCoinFavouriteStatus(favouriteCoinId = favouriteCoinId)
    }

    private suspend fun changeCoinFavouriteStatus(favouriteCoinId: FavouriteCoinId) {
        return favouriteCoinIdRepository.changeFavouriteStatus(favouriteCoinId = favouriteCoinId)
    }
}
