package com.example.cryptolistapp.home.data.source.local.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

data class Percentage(private val percentage: String?) {
    companion object {
        private val percentageFormat: NumberFormat =
            NumberFormat.getPercentInstance(Locale.US).apply {
                isGroupingUsed = true
                minimumFractionDigits = 2
                maximumFractionDigits = 2
            }
    }

    val amount: BigDecimal = percentage.toSanitisedBigDecimalOrZero()

    private val roundedAmount: BigDecimal = amount.setScale(2, RoundingMode.HALF_EVEN)
    val isPositive: Boolean = roundedAmount.signum() > 0
    val isNegative: Boolean = roundedAmount.signum() < 0

    val formattedAmount: String =
        when {
            percentage == null -> "-- %"
            isNegative -> percentageFormat.format(amount.divide(BigDecimal("100")))
            else -> "+" + percentageFormat.format(amount.divide(BigDecimal("100")))
        }
}

fun String?.toSanitisedBigDecimalOrZero(): BigDecimal {
    return try {
        if (this != null) {
            val sanitisedString = this
                .filterNot { it == ',' }
                .trim()

            BigDecimal(sanitisedString)
        } else {
            BigDecimal.ZERO
        }
    } catch (e: NumberFormatException) {
        BigDecimal.ZERO
    }
}
