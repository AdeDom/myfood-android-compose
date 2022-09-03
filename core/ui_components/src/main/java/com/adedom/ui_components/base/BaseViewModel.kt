package com.adedom.ui_components.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.coroutines.CoroutineContext

/**
 *@param S :: State - render ui
 *@param E :: Event - nav or single live event
 *@param A :: Action - ui action
 */
abstract class BaseViewModel<S : Any, E : Any, A : Any>(
    initialUiState: S,
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    var uiState by mutableStateOf(initialUiState)
        private set

    private val _uiEvent = MutableSharedFlow<E>()
    val uiEvent: SharedFlow<E> = _uiEvent.asSharedFlow()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + exceptionHandler

    protected fun setState(reducer: S.() -> S) {
        uiState = uiState.reducer()
    }

    protected suspend fun coState(reducer: suspend S.() -> S) {
        uiState = uiState.reducer()
    }

    protected fun setEvent(event: E) {
        launch {
            _uiEvent.emit(event)
        }
    }

    abstract fun dispatch(action: A)

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}