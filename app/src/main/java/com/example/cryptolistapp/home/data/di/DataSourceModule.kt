package com.example.cryptolistapp.home.data.di

import com.example.cryptolistapp.home.data.source.local.LocalCoinDataSource
import com.example.cryptolistapp.home.data.source.local.LocalCoinDataSourceImpl
import com.example.cryptolistapp.home.data.source.local.dao.CoinDao
import com.example.cryptolistapp.home.data.source.local.dao.FavouriteCoinDao
import com.example.cryptolistapp.home.data.source.local.dao.FavouriteCoinIdDao
import com.example.cryptolistapp.home.data.source.remote.remote.CoinApi
import com.example.cryptolistapp.home.data.source.remote.remote.CoinNetworkDataSource
import com.example.cryptolistapp.home.data.source.remote.remote.CoinNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCoinNetworkDataSource(coinApi: CoinApi): CoinNetworkDataSource {
        return CoinNetworkDataSourceImpl(coinApi = coinApi)
    }

    @Provides
    @Singleton
    fun provideCoinLocalDataSource(
        favouriteCoinIdDao: FavouriteCoinIdDao,
        coinDao: CoinDao,
        favouriteCoinDao: FavouriteCoinDao
    ): LocalCoinDataSource {
        return LocalCoinDataSourceImpl(
            favouriteCoinIdDao = favouriteCoinIdDao,
            coinDao = coinDao,
            favouriteCoinDao = favouriteCoinDao
        )
    }
}
