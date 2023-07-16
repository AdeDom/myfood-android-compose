package com.adedom.main.utils.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BackHandlerEffect {
    private var isBackPressed = false
    private var backPressedJob: Job? = null

    private val _effect = Channel<Boolean>()
    val effect: Flow<Boolean> = _effect.receiveAsFlow()

    fun onBack() {
        MainScope().launch {
            backPressedJob?.cancel()
            backPressedJob = launch {
                _effect.send(isBackPressed)
                isBackPressed = true
                delay(2_000)
                isBackPressed = false
            }
        }
    }
}

@Composable
fun rememberBackHandlerEffect() = remember { BackHandlerEffect() }
