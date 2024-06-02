package com.example.cryptolistapp.home.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cryptolistapp.home.data.source.local.dao.CoinDao
import com.example.cryptolistapp.home.data.source.local.dao.FavouriteCoinDao
import com.example.cryptolistapp.home.data.source.local.dao.FavouriteCoinIdDao
import com.example.cryptolistapp.home.data.source.local.mapper.ImmutableListTypeConverter
import com.example.cryptolistapp.home.data.source.local.mapper.PercentageTypeConverter
import com.example.cryptolistapp.home.data.source.local.mapper.PriceTypeConverter
import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoin
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId

@Database(
    version = 1,
    entities = [Coin::class, FavouriteCoin::class, FavouriteCoinId::class],
)
@TypeConverters(
    PriceTypeConverter::class,
    PercentageTypeConverter::class,
    ImmutableListTypeConverter::class
)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun favouriteCoinDao(): FavouriteCoinDao
    abstract fun favouriteCoinIdDao(): FavouriteCoinIdDao
}
