package com.pavellukyanov.androidgym.base

import CoroutineHelper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Reducer<STATE : State, ACTION : Action, EFFECT : Effect>(initState: STATE) : ViewModel() {
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initState)
    protected val coroutineHelper = CoroutineHelper(viewModelScope)

    val state: StateFlow<STATE> = _state.asStateFlow()
    val effect: Channel<EFFECT> = Channel()

    protected abstract suspend fun reduce(oldState: STATE, action: ACTION)

    fun sendAction(action: ACTION) = coroutineHelper.launchCPU {
        reduce(_state.value, action)
    }

    protected fun saveState(newState: STATE) = coroutineHelper.launchUI {
        _state.value = newState
    }

    protected fun sendEffect(newEffect: EFFECT) = coroutineHelper.launchUI {
        effect.send(newEffect)
    }
}