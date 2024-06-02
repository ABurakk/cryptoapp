package com.example.cryptolistapp.home.data.source.local.mapper

import androidx.room.TypeConverter
import com.example.cryptolistapp.home.data.source.local.model.Price
import com.google.gson.Gson

class PriceTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPrice(price: Price): String {
        return gson.toJson(price)
    }

    @TypeConverter
    fun toPrice(priceJson: String): Price {
        return gson.fromJson(priceJson, Price::class.java)
    }
}
