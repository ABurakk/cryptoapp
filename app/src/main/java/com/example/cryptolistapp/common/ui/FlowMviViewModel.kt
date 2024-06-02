package com.example.cryptolistapp.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class FlowMviViewModel<Event, State>(defaultState: State) : ViewModel() {

    val events = MutableSharedFlow<Event>()

    protected val internalState = MutableStateFlow(defaultState)
    val state: StateFlow<State> get() = internalState

    fun dispatch(event: Event) {
        viewModelScope.launch { events.emit(event) }
    }

    inline fun <reified SpecificEvent : Event> on(
        crossinline handle: suspend (event: SpecificEvent) -> Unit,
    ) {
        on(
            events = events,
            scope = viewModelScope,
            onError = this::onError,
            handle = handle,
        )
    }

    protected inline fun <reified SpecificEvent : Event> onClick(
        crossinline handle: suspend (event: SpecificEvent) -> Unit,
    ) {
        onClick(
            events = events,
            scope = viewModelScope,
            onError = this::onError,
            handle = handle,
        )
    }

    open fun onError(throwable: Throwable) = Unit
}

inline fun <Event, reified SpecificEvent : Event> on(
    events: SharedFlow<Event>,
    scope: CoroutineScope,
    noinline onError: (Throwable) -> Unit = {},
    crossinline handle: suspend (event: SpecificEvent) -> Unit,
) {
    events
        .filterIsInstance<SpecificEvent>()
        .map { runCatching { handle(it) } }
        .map { it.exceptionOrNull() }
        .filterNotNull()
        .onEach(onError)
        .launchIn(scope)
}

inline fun <Event, reified SpecificEvent : Event> onClick(
    events: SharedFlow<Event>,
    scope: CoroutineScope,
    noinline onError: (Throwable) -> Unit = {},
    crossinline handle: suspend (event: SpecificEvent) -> Unit,
) {
    events
        .filterIsInstance<SpecificEvent>()
        .throttleFirst(300L)
        .map { runCatching { handle(it) } }
        .map { it.exceptionOrNull() }
        .filterNotNull()
        .onEach(onError)
        .launchIn(scope)
}

fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "Period must be positive" }
    return flow {
        var lastTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                emit(value)
            }
        }
    }
}
