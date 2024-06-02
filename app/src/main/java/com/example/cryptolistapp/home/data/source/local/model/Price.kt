package com.example.cryptolistapp.home.data.source.local.model

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

data class Price(val price: String?, val currency: AppCurrency = AppCurrency.USD) :
    Comparable<Price> {
    val amount: BigDecimal = price?.toBigDecimalOrNull() ?: BigDecimal.ZERO

    private val currencyFormat: DecimalFormat = getCurrencyFormat()

    val formattedAmount: String = when {
        price.isNullOrBlank() -> "${currency.symbol}--"
        else -> currencyFormat.format(amount)
    }

    private fun getCurrencyFormat(): DecimalFormat {
        val currencyFormat = DecimalFormat.getCurrencyInstance(Locale.US) as DecimalFormat
        currencyFormat.currency = java.util.Currency.getInstance(currency.name)
        return currencyFormat
    }

    override fun compareTo(other: Price) = amount.compareTo(other.amount)
}

enum class AppCurrency(val symbol: String, val currencyName: String) {
    USD("$", "USD"),
}