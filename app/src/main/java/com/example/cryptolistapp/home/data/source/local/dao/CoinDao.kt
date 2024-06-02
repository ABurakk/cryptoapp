package com.example.cryptolistapp.home.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cryptolistapp.home.data.source.local.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM Coin")
    fun getCoins(): Flow<List<Coin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: List<Coin>)

    @Query("DELETE FROM Coin")
    suspend fun deleteAllCoins()

    @Transaction
    suspend fun updateCoins(coins: List<Coin>) {
        deleteAllCoins()
        insertCoins(coins)
    }
}
