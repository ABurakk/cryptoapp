package com.example.cryptolistapp.home.data.di

import com.example.cryptolistapp.detail.data.detail.CoinDetailsMapper
import com.example.cryptolistapp.detail.data.detail.CoinDetailsRepository
import com.example.cryptolistapp.detail.data.detail.CoinDetailsRepositoryImpl
import com.example.cryptolistapp.detail.data.favorite.FavouriteCoinIdRepository
import com.example.cryptolistapp.detail.data.favorite.FavouriteCoinIdRepositoryImpl
import com.example.cryptolistapp.home.data.mapper.CoinMapper
import com.example.cryptolistapp.home.data.repository.CoinRepository
import com.example.cryptolistapp.home.data.repository.CoinRepositoryImpl
import com.example.cryptolistapp.home.data.source.local.LocalCoinDataSource
import com.example.cryptolistapp.home.data.source.remote.remote.CoinNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoinRepository(
        coinNetworkDataSource: CoinNetworkDataSource,
        localCoinDataSource: LocalCoinDataSource,
        coinMapper: CoinMapper,
    ): CoinRepository {
        return CoinRepositoryImpl(
            coinNetworkDataSource = coinNetworkDataSource,
            localCoinDataSource = localCoinDataSource,
            coinMapper = coinMapper,
        )
    }

    @Provides
    @Singleton
    fun provideCoinDetailsRepository(
        coinNetworkDataSource: CoinNetworkDataSource,
        coinDetailsMapper: CoinDetailsMapper,
    ): CoinDetailsRepository {
        return CoinDetailsRepositoryImpl(
            coinNetworkDataSource = coinNetworkDataSource,
            coinDetailsMapper = coinDetailsMapper,
        )
    }

    @Provides
    @Singleton
    fun provideFavouriteCoinIdRepository(
        coinLocalDataSource: LocalCoinDataSource,
    ): FavouriteCoinIdRepository {
        return FavouriteCoinIdRepositoryImpl(
            coinLocalDataSource = coinLocalDataSource,
        )
    }
}
