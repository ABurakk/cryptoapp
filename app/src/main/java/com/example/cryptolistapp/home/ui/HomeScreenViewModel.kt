package com.example.cryptolistapp.home.ui

import androidx.lifecycle.viewModelScope
import com.example.cryptolistapp.common.ui.TimeOfDay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalTime
import kotlin.time.Duration.Companion.minutes
import com.example.cryptolistapp.common.Result
import com.example.cryptolistapp.common.ui.FlowMviViewModel
import com.example.cryptolistapp.home.domain.GetCoinsUseCase
import com.example.cryptolistapp.home.domain.RefreshLocalCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
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
        on<HomeScreenEvent.Initialise> {
            internalState.update { it.copy(isLoading = true) }

            viewModelScope.launch {
                combine(
                    getCoinsUseCase(),
                    getCurrentHourFlow()
                ) { coinsResult, currentHour ->
                    coinsResult to currentHour
                }.collect { (results, currentHour) ->
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
                                val errorMessages = it.errorMessage + ""
                                it.copy(
                                    errorMessage = errorMessages,
                                    isLoading = false
                                )
                            }
                        }
                    }
                    updateCachedCoins()
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

        on<HomeScreenEvent.DismissErrorMessage> { event ->
            internalState.update {
                it.copy(errorMessage = null)
            }
        }
    }

    private fun getCurrentHourFlow(): Flow<Int> = flow {
        while (true) {
            val currentHour = LocalTime.now().hour
            emit(currentHour)
            delay(5.minutes.inWholeMilliseconds)
        }
    }

    private suspend fun updateCachedCoins() {
        val result = updateCachedCoinsUseCase()

        if (result is Result.Error) {
            internalState.update {
                it.copy(errorMessage = result.message)
            }
        }
    }
}