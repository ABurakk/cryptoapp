package com.example.cryptolistapp.home.data.source.local

import com.example.cryptolistapp.home.data.source.local.dao.CoinDao
import com.example.cryptolistapp.home.data.source.local.dao.FavouriteCoinDao
import com.example.cryptolistapp.home.data.source.local.dao.FavouriteCoinIdDao
import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoin
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId
import kotlinx.coroutines.flow.Flow

class LocalCoinDataSourceImpl(
    private val coinDao: CoinDao,
    private val favouriteCoinDao: FavouriteCoinDao,
    private val favouriteCoinIdDao: FavouriteCoinIdDao
) : LocalCoinDataSource {
    override fun fetchAllCoins(): Flow<List<Coin>> {
        return coinDao.getCoins()
    }

    override suspend fun refreshCoins(coins: List<Coin>) {
        coinDao.updateCoins(coins)
    }

    override fun fetchFavouriteCoins(): Flow<List<FavouriteCoin>> {
        return favouriteCoinDao.getFavouriteCoins()
    }

    override suspend fun refreshFavouriteCoins(favouriteCoins: List<FavouriteCoin>) {
        favouriteCoinDao.updateFavouriteCoins(favouriteCoins)
    }

    override fun fetchFavouriteCoinIds(): Flow<List<FavouriteCoinId>> {
        return favouriteCoinIdDao.getFavouriteCoinIds()
    }

    override fun checkIfCoinIsFavourite(favouriteCoinId: FavouriteCoinId): Flow<Boolean> {
        return favouriteCoinIdDao.isCoinFavourite(coinId = favouriteCoinId.id)
    }

    override suspend fun changeFavouriteStatus(favouriteCoinId: FavouriteCoinId) {
        val isCoinFavourite = favouriteCoinIdDao.isCoinFavouriteOneShot(favouriteCoinId.id)

        if (isCoinFavourite) {
            favouriteCoinIdDao.delete(favouriteCoinId)
        } else {
            favouriteCoinIdDao.insert(favouriteCoinId)
        }
    }
}
