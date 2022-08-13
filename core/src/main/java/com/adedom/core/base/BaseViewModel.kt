package com.adedom.core.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adedom.core.utils.RefreshTokenExpiredException
import com.adedom.myfood.data.models.base.BaseError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : Any, A : Any>(initialUiState: S) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        if (throwable is RefreshTokenExpiredException) {
            launch {
                val baseError = throwable.toBaseError()
                _refreshTokenExpired.emit(baseError)
            }
        }
    }

    var uiState by mutableStateOf(initialUiState)
        protected set

    protected val _uiEvent = MutableSharedFlow<A>()
    val uiEvent: SharedFlow<A> = _uiEvent.asSharedFlow()

    private val _refreshTokenExpired = MutableSharedFlow<BaseError>()
    val refreshTokenExpired: SharedFlow<BaseError> = _refreshTokenExpired.asSharedFlow()

    fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(coroutineExceptionHandler, block = block)
    }
}