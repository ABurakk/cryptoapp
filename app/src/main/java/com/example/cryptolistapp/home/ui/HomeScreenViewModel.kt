package com.example.cryptolistapp.home.ui

import androidx.lifecycle.viewModelScope
import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.common.ui.FlowMviViewModel
import com.example.cryptolistapp.home.data.repository.CoinError
import com.example.cryptolistapp.home.domain.GetCoinsUseCase
import com.example.cryptolistapp.home.domain.RefreshLocalCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val updateCachedCoinsUseCase: RefreshLocalCoinsUseCase,
) : FlowMviViewModel<HomeScreenEvent, HomeScreenState>(HomeScreenState()) {

    init {
        handleEvents()
    }

    private fun handleEvents() {
        on<HomeScreenEvent.ScreenOpened> {
            updateCachedCoinsUseCase()
            internalState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                combine(
                    getCoinsUseCase(),
                ) { coinsResult ->
                    coinsResult
                }.collect { (results) ->
                    when (results) {
                        is Result.Success -> {
                            internalState.update {
                                it.copy(
                                    coins = results.data.toImmutableList(),
                                    isLoading = false
                                )
                            }
                        }

                        is Result.Error -> {
                            internalState.update {
                                it.copy(
                                    errorMessage = CoinError.NETWORK_ERROR.message,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }

        on<HomeScreenEvent.PullRefresh> {
            viewModelScope.launch {
                internalState.update { it.copy(isRefreshing = true) }
                delay(250.milliseconds)
                updateCachedCoins()
                internalState.update { it.copy(isRefreshing = false) }
            }
        }

        on<HomeScreenEvent.SortCoins> { event ->
            viewModelScope.launch {
                internalState.update {
                    it.copy(
                        coinSort = event.coinSort,
                        isLoading = true
                    )
                }
                updateCachedCoinsUseCase(coinSort = event.coinSort)
                internalState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }

        on<HomeScreenEvent.DismissErrorMessage> { event ->
            internalState.update {
                it.copy(errorMessage = null)
            }
        }
    }

    private suspend fun updateCachedCoins() {
        val result = updateCachedCoinsUseCase()
        if (result is Result.Error) {
            internalState.update {
                it.copy(
                    errorMessage = result.message,
                    isLoading = false
                )
            }
        }
    }
}