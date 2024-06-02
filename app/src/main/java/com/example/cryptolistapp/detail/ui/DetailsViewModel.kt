package com.example.cryptolistapp.detail.ui

import androidx.lifecycle.viewModelScope
import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.common.ui.FlowMviViewModel
import com.example.cryptolistapp.detail.domain.ChangeCoinFavouriteStatusUseCase
import com.example.cryptolistapp.detail.domain.GetCoinDetailsUseCase
import com.example.cryptolistapp.detail.domain.IsCoinFavouriteUseCase
import com.example.cryptolistapp.home.data.source.local.model.FavouriteCoinId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val isCoinFavouriteUseCase: IsCoinFavouriteUseCase,
    private val changeCoinFavouriteStatusUseCase: ChangeCoinFavouriteStatusUseCase
) : FlowMviViewModel<DetailsEvent, DetailsState>(DetailsState(isLoading = true)) {

    private var coinId: String? = null

    init {
        handleEvents()
    }

    private fun initialiseUiState(coinId: String) {
        this.coinId = coinId
        viewModelScope.launch {
            combine(
                getCoinDetailsUseCase(coinId = coinId),
                isCoinFavouriteUseCase(favouriteCoinId = FavouriteCoinId(id = coinId))
            ) { coinDetailsResult, isCoinFavouriteResult ->
                Pair(coinDetailsResult, isCoinFavouriteResult)
            }.collect { (coinDetailsResult, isCoinFavouriteResult) ->
                when {
                    coinDetailsResult is Result.Error -> {
                        dispatch(
                            DetailsEvent.ShowError(
                                coinDetailsResult.message ?: "Unknown error"
                            )
                        )
                    }

                    isCoinFavouriteResult is Result.Error -> {
                        dispatch(
                            DetailsEvent.ShowError(
                                isCoinFavouriteResult.message ?: "Unknown error"
                            )
                        )
                    }

                    coinDetailsResult is Result.Success &&
                            isCoinFavouriteResult is Result.Success -> {
                        internalState.update {
                            DetailsState(
                                coinDetails = coinDetailsResult.data,
                                isCoinFavourite = isCoinFavouriteResult.data,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleEvents() {
        on<DetailsEvent.ScreenOpened> { event ->
            initialiseUiState(event.id)
        }

        on<DetailsEvent.ToggleIsCoinFavourite> {
            val coinId = this.coinId ?: return@on

            viewModelScope.launch {
                val favouriteCoinId = FavouriteCoinId(id = coinId)
                changeCoinFavouriteStatusUseCase(favouriteCoinId = favouriteCoinId)
            }
        }

        on<DetailsEvent.Retry> {
            val coinId = this.coinId ?: return@on
            initialiseUiState(coinId)
        }

        on<DetailsEvent.ShowError> { event ->
            internalState.update {
                it.copy(
                    errorMessage = event.errorMessage,
                    isLoading = false
                )
            }
        }
    }
}