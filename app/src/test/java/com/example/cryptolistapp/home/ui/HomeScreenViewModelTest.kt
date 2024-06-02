package com.example.cryptolistapp.home.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.home.data.source.local.model.Coin
import com.example.cryptolistapp.home.data.source.local.model.Percentage
import com.example.cryptolistapp.home.data.source.local.model.Price
import com.example.cryptolistapp.home.domain.CoinSort
import com.example.cryptolistapp.home.domain.GetCoinsUseCase
import com.example.cryptolistapp.home.domain.RefreshLocalCoinsUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeScreenViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getCoinsUseCase: GetCoinsUseCase = mockk()
    private val updateCachedCoinsUseCase: RefreshLocalCoinsUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeScreenViewModel(getCoinsUseCase, updateCachedCoinsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `When ScreenOpened event Then state should be updated with coins`() = runTest {
        val coins = listOf(
            Coin(
                id = "1",
                name = "Bitcoin",
                symbol = "BTC",
                imageUrl = "url",
                currentPrice = Price("50000.0"),
                priceChangePercentage24h = Percentage("1.0")
            )
        )
        val result = Result.Success(coins)
        coEvery { getCoinsUseCase() } returns flowOf(result)
        coJustRun { updateCachedCoinsUseCase() }

        viewModel.dispatch(HomeScreenEvent.ScreenOpened)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.coins).isEqualTo(coins)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `When ScreenOpened event Then state should be updated with error`() = runTest {
        val errorMessage = "Network error: Could not retrieve coin data"
        val result: Result.Error<List<Coin>> = Result.Error(errorMessage)
        coEvery { getCoinsUseCase() } returns flowOf(result)
        coJustRun { updateCachedCoinsUseCase() }

        viewModel.dispatch(HomeScreenEvent.ScreenOpened)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.errorMessage).isEqualTo(errorMessage)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `When SortCoins event Then state should be updated with sorted coins`() = runTest {
        val coinSort = CoinSort.Newest
        coJustRun { updateCachedCoinsUseCase(coinSort) }

        viewModel.dispatch(HomeScreenEvent.SortCoins(coinSort))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.coinSort).isEqualTo(coinSort)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `When DismissErrorMessage event Then error message should be cleared`() = runTest {
        viewModel.dispatch(HomeScreenEvent.DismissErrorMessage)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.errorMessage).isNull()
    }
}
