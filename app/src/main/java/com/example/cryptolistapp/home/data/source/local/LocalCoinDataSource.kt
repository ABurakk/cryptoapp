package com.example.cryptolistapp.home.data.source.local

import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoin
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow

interface LocalCoinDataSource {
    fun fetchAllCoins(): Flow<List<Coin>>
    suspend fun refreshCoins(coins: List<Coin>)
    fun fetchFavouriteCoins(): Flow<List<FavouriteCoin>>
    suspend fun refreshFavouriteCoins(favouriteCoins: List<FavouriteCoin>)
    fun fetchFavouriteCoinIds(): Flow<List<FavouriteCoinId>>
    fun checkIfCoinIsFavourite(favouriteCoinId: FavouriteCoinId): Flow<Boolean>
    suspend fun changeFavouriteStatus(favouriteCoinId: FavouriteCoinId)
}
