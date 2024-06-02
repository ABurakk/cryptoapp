package com.example.cryptolistapp.home.data.source.local.mapper

import androidx.room.TypeConverter
import com.example.cryptolistapp.home.data.source.local.model.Percentage
import com.google.gson.Gson

class PercentageTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPercentage(percentage: Percentage): String {
        return gson.toJson(percentage)
    }

    @TypeConverter
    fun toPercentage(percentageJson: String): Percentage {
        return gson.fromJson(percentageJson, Percentage::class.java)
    }
}