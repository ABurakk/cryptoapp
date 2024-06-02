package com.example.cryptolistapp.home.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.example.cryptolistapp.home.data.source.local.model.Percentage

@Composable
fun PriceChange(
    percentage: Percentage,
    modifier: Modifier = Modifier
) {
    val textColor = when {
        percentage.isPositive -> Color.Green
        percentage.isNegative -> Color.Red
        else -> MaterialTheme.colorScheme.onSurface
    }

    Text(
        text = percentage.formattedAmount,
        style = MaterialTheme.typography.bodyMedium,
        color = textColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}