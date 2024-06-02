package com.example.cryptolistapp.home.data.di

import android.content.Context
import androidx.room.Room
import com.example.cryptolistapp.common.Constants
import com.example.cryptolistapp.home.data.source.local.CoinDatabase
import com.example.cryptolistapp.home.data.source.local.dao.CoinDao
import com.example.cryptolistapp.home.data.source.local.dao.FavouriteCoinDao
import com.example.cryptolistapp.home.data.source.local.dao.FavouriteCoinIdDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideCoinDao(database: CoinDatabase): CoinDao {
        return database.coinDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteCoinDao(database: CoinDatabase): FavouriteCoinDao {
        return database.favouriteCoinDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteCoinIdDao(database: CoinDatabase): FavouriteCoinIdDao {
        return database.favouriteCoinIdDao()
    }

    @Provides
    @Singleton
    fun provideCoinDatabase(@ApplicationContext context: Context): CoinDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CoinDatabase::class.java,
            Constants.COIN_DATABASE_NAME
        ).build()
    }
}
