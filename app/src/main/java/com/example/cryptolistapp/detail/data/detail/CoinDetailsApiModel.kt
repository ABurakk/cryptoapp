package com.example.cryptolistapp.detail.data.detail

import com.google.gson.annotations.SerializedName

data class CoinDetailsApiModel(
    @SerializedName("data")
    val coinDetailsDataHolder: CoinDetailsDataHolder?
)

data class CoinDetailsDataHolder(
    @SerializedName("coin")
    val coinDetailsData: CoinDetailsData?
)

data class CoinDetailsData(
    @SerializedName("uuid")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("iconUrl")
    val imageUrl: String?,
    @SerializedName("price")
    val currentPrice: String?
)