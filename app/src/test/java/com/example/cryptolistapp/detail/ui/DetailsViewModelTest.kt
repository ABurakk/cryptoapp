package com.example.cryptolistapp.detail.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.detail.domain.ChangeCoinFavouriteStatusUseCase
import com.example.cryptolistapp.detail.domain.CoinDetails
import com.example.cryptolistapp.detail.domain.GetCoinDetailsUseCase
import com.example.cryptolistapp.detail.domain.IsCoinFavouriteUseCase
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId
import com.example.cryptolistapp.home.data.source.local.model.Price
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
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
internal class DetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getCoinDetailsUseCase: GetCoinDetailsUseCase = mockk()
    private val isCoinFavouriteUseCase: IsCoinFavouriteUseCase = mockk()
    private val changeCoinFavouriteStatusUseCase: ChangeCoinFavouriteStatusUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(
            getCoinDetailsUseCase,
            isCoinFavouriteUseCase,
            changeCoinFavouriteStatusUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `When ScreenOpened event Then state should be updated with coin details`() = runTest {
        val coinDetails = CoinDetails(
            id = "1",
            name = "Bitcoin",
            symbol = "BTC",
            imageUrl = "url",
            currentPrice = Price("50000.0"),
        )
        val result = Result.Success(coinDetails)
        coEvery { getCoinDetailsUseCase("1") } returns flowOf(result)
        coEvery { isCoinFavouriteUseCase(FavouriteCoinId("1")) } returns flowOf(Result.Success(true))

        viewModel.dispatch(DetailsEvent.ScreenOpened("1"))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.coinDetails).isEqualTo(coinDetails)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `When ScreenOpened event Then state should be updated with error`() = runTest {
        val errorMessage = "Error occurred"
        val result: Result.Error<CoinDetails> = Result.Error(errorMessage)
        coEvery { getCoinDetailsUseCase("1") } returns flowOf(result)
        coEvery { isCoinFavouriteUseCase(FavouriteCoinId("1")) } returns flowOf(Result.Success(true))

        viewModel.dispatch(DetailsEvent.ScreenOpened("1"))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.errorMessage).isEqualTo(errorMessage)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `When ToggleIsCoinFavourite event Then favourite status should be toggled`() = runTest {
        val coinDetails = CoinDetails(
            id = "1",
            name = "Bitcoin",
            symbol = "BTC",
            imageUrl = "url",
            currentPrice = Price("50000.0"),
        )
        val result = Result.Success(coinDetails)
        coEvery { getCoinDetailsUseCase("1") } returns flowOf(result)
        coEvery { isCoinFavouriteUseCase(FavouriteCoinId("1")) } returns flowOf(Result.Success(true))
        coJustRun { changeCoinFavouriteStatusUseCase(FavouriteCoinId("1")) }

        viewModel.dispatch(DetailsEvent.ScreenOpened("1"))
        advanceUntilIdle()

        viewModel.dispatch(DetailsEvent.ToggleIsCoinFavourite)
        advanceUntilIdle()

        coVerify(exactly = 1) { changeCoinFavouriteStatusUseCase(FavouriteCoinId("1")) }
    }
}
