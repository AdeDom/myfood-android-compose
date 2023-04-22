package com.adedom.ui_components.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<Event, State>(
    initialUiState: State,
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    var uiState by mutableStateOf(initialUiState)
        protected set

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + exceptionHandler

    protected fun emit(reducer: State.() -> State) {
        uiState = uiState.reducer()
    }

    protected suspend fun coEmit(reducer: suspend State.() -> State) {
        uiState = uiState.reducer()
    }

    abstract fun onEvent(event: Event)

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}