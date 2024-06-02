package com.example.cryptolistapp.detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptolistapp.R
import com.example.cryptolistapp.app.navigation.CurrentCoinHolder
import com.example.cryptolistapp.common.ui.components.ErrorScreen
import com.example.cryptolistapp.common.ui.components.LoadingIndicator
import com.example.cryptolistapp.common.ui.components.OnScreenStarted
import com.example.cryptolistapp.detail.domain.CoinDetails
import com.example.cryptolistapp.detail.ui.components.CoinDetailsItem

@Composable
fun DetailPage(
    onBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val coinId = remember { CurrentCoinHolder.currentCoinId }
    OnScreenStarted {
        viewModel.dispatch(DetailsEvent.ScreenOpened(coinId ?: ""))
    }
    DetailScreen(
        uiState = uiState,
        onBack = onBack,
        onToggleFavourite = {
            viewModel.dispatch(DetailsEvent.ToggleIsCoinFavourite)
        }
    )
}

@Composable
fun DetailScreen(
    uiState: DetailsState,
    onBack: () -> Unit,
    onToggleFavourite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DetailScreenTopBar(onBack = onBack)
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingIndicator(modifier = Modifier.padding(paddingValues))
            }

            uiState.errorMessage != null -> {
                ErrorScreen(
                    message = uiState.errorMessage,
                    modifier = Modifier.padding(paddingValues),
                )
            }

            uiState.coinDetails != null -> {
                CoinDetailsContent(
                    coinDetails = uiState.coinDetails,
                    isFavourite = uiState.isCoinFavourite,
                    onToggleFavourite = onToggleFavourite,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun CoinDetailsContent(
    coinDetails: CoinDetails,
    isFavourite: Boolean,
    onToggleFavourite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CoinDetailsItem(
            coin = coinDetails,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Price: ${coinDetails.currentPrice.formattedAmount}",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(
            onClick = onToggleFavourite,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = if (isFavourite) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = if (isFavourite) "Unfavourite" else "Favourite",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailScreenTopBar(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.coin_details)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        modifier = modifier
    )
}