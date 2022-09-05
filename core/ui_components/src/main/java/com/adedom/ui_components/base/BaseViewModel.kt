package com.adedom.ui_components.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<Event : Any, State : Any>(
    initialUiState: State,
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    var uiState by mutableStateOf(initialUiState)
        private set

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + exceptionHandler

    protected fun setState(reducer: State.() -> State) {
        uiState = uiState.reducer()
    }

    protected suspend fun coState(reducer: suspend State.() -> State) {
        uiState = uiState.reducer()
    }

    abstract fun dispatch(event: Event)

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}