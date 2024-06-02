package com.example.cryptolistapp.home.domain

import androidx.annotation.StringRes
import com.example.cryptolistapp.R

enum class CoinSort(@StringRes val nameId: Int) {
    MarketCap(R.string.home_screen_sort_market_cap),
    Popular(R.string.home_screen_sort_popular),
    Gainers(R.string.home_screen_sort_gainers),
    Losers(R.string.home_screen_sort_losers),
    Newest(R.string.home_screen_sort_newest),
}
