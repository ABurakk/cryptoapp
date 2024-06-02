package com.example.cryptolistapp.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.cryptolistapp.home.data.source.local.model.Coin

@Composable
fun CoinItem(
    coin: Coin,
    onCoinClick: (Coin) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageRequest = remember {
        ImageRequest.Builder(context)
            .data(coin.imageUrl)
            .decoderFactory(SvgDecoder.Factory())
            .crossfade(true)
            .build()
    }

    Surface(
        shape = RectangleShape,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCoinClick(coin) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = coin.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = coin.currentPrice.formattedAmount,
                    style = MaterialTheme.typography.bodyMedium
                )

                PriceChange(percentage = coin.priceChangePercentage24h)
            }
        }
    }
}