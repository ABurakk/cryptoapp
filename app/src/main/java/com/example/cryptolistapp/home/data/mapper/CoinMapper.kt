package com.example.cryptolistapp.home.data.mapper

import com.example.cryptolistapp.data.source.remote.CoinsApiModel
import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.data.source.local.model.Percentage
import com.example.cryptolistapp.home.data.source.local.model.Price
import javax.inject.Inject

class CoinMapper @Inject constructor() {
    fun mapApiModelToModel(apiModel: CoinsApiModel): List<Coin> {
        val validCoins = apiModel.coinsData?.coins
            .orEmpty()
            .filterNotNull()
            .filter { it.id != null }

        return validCoins.map { coinApiModel ->
            Coin(
                id = coinApiModel.id!!,
                name = coinApiModel.name.orEmpty(),
                symbol = coinApiModel.symbol.orEmpty(),
                imageUrl = coinApiModel.imageUrl.orEmpty(),
                currentPrice = Price(coinApiModel.currentPrice),
                priceChangePercentage24h = Percentage(coinApiModel.priceChangePercentage24h),
            )
        }
    }
}
