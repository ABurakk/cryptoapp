package com.example.cryptolistapp.home.ui


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptolistapp.common.ui.components.LoadingIndicator
import com.example.cryptolistapp.common.ui.components.OnScreenStarted
import com.example.cryptolistapp.common.ui.pullrefresh.PullRefreshIndicator
import com.example.cryptolistapp.common.ui.pullrefresh.pullRefresh
import com.example.cryptolistapp.common.ui.pullrefresh.rememberPullRefreshState
import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.domain.CoinSort
import com.example.cryptolistapp.home.ui.components.CoinItem
import com.example.cryptolistapp.home.ui.components.CoinSortChip

@Composable
fun HomePage(
    onNavigateDetails: (String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    OnScreenStarted {
        viewModel.dispatch(HomeScreenEvent.ScreenOpened)
    }
    HomeScreen(
        uiState = uiState,
        onCoinClick = { coin ->
            onNavigateDetails(coin.id)
        },
        onCoinSort = {
            viewModel.dispatch(HomeScreenEvent.SortCoins(it))
        },
        onRefresh = {
            viewModel.dispatch(HomeScreenEvent.PullRefresh)
        },
        onDismissError = {
            viewModel.dispatch(HomeScreenEvent.DismissErrorMessage)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeScreenState,
    onCoinClick: (Coin) -> Unit,
    onCoinSort: (CoinSort) -> Unit,
    onRefresh: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = onRefresh
    )

    Scaffold(
        topBar = {
            HomeScreenTopBar(
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { scaffoldPadding ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .padding(scaffoldPadding)
        ) {
            HomeScreenContent(
                uiState = uiState,
                coins = uiState.coins,
                onCoinClick = onCoinClick,
                onUpdateCoinSort = {
                    onCoinSort(it)
                },
                lazyListState = listState,
                coinSort = uiState.coinSort
            )

            PullRefreshIndicator(
                refreshing = uiState.isRefreshing,
                state = pullRefreshState,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )

            if (uiState.errorMessage != null) {
                LaunchedEffect(uiState.errorMessage, snackbarHostState) {
                    snackbarHostState.showSnackbar(message = uiState.errorMessage)
                    onDismissError()
                }
            }
        }
    }
}


@Composable
fun HomeScreenContent(
    uiState: HomeScreenState,
    coins: List<Coin>,
    onCoinClick: (Coin) -> Unit,
    coinSort: CoinSort,
    onUpdateCoinSort: (CoinSort) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    if (coins.isEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {}
    } else {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
            modifier = modifier
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 8.dp)
                ) {
                    CoinSort.entries.forEach { coinSortEntry ->
                        CoinSortChip(
                            coinSort = coinSortEntry,
                            selected = coinSortEntry == coinSort,
                            onClick = { onUpdateCoinSort(coinSortEntry) }
                        )
                    }
                }
            }
            if (uiState.isLoading) {
                item {
                    LoadingIndicator()
                }
            } else {
                items(
                    count = coins.size,
                    key = { coins[it].id },
                    itemContent = { index ->
                        val coinListItem = coins[index]

                        CoinItem(
                            coin = coinListItem,
                            onCoinClick = { onCoinClick(coinListItem) },
                        )
                    }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.animateContentSize()) {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        actions = {

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

