package com.example.cryptolistapp.detail.data.detail

import com.example.cryptolistapp.detail.domain.CoinDetails
import com.example.cryptolistapp.home.data.source.local.model.Price
import javax.inject.Inject

class CoinDetailsMapper @Inject constructor() {

    fun mapApiModelToModel(apiModel: CoinDetailsApiModel): CoinDetails {
        val coinDetails = apiModel.coinDetailsDataHolder?.coinDetailsData

        return CoinDetails(
            id = coinDetails?.id.orEmpty(),
            name = coinDetails?.name.orEmpty(),
            symbol = coinDetails?.symbol.orEmpty(),
            imageUrl = coinDetails?.imageUrl.orEmpty(),
            currentPrice = Price(coinDetails?.currentPrice)
        )
    }
}
