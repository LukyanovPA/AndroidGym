package com.pavellukyanov.androidgym.base

import CoroutineHelper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import error.ErrorStorage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.java.KoinJavaComponent.inject

abstract class Reducer<STATE : State, ACTION : Action, EFFECT : Effect>(initState: STATE) : ViewModel() {
    private val errorStorage by inject<ErrorStorage>(ErrorStorage::class.java)
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initState)
    protected val coroutineHelper = CoroutineHelper(viewModelScope)

    val state: Flow<STATE> = _state
    val effect: Channel<EFFECT> = Channel()

    protected abstract suspend fun reduce(oldState: STATE, action: ACTION)

    fun sendAction(action: ACTION) = coroutineHelper.launchCPU {
        reduce(_state.value, action)
    }

    protected fun saveState(newState: STATE) = coroutineHelper.launchCPU {
        _state.emit(newState)
    }

    protected fun sendEffect(newEffect: EFFECT) = coroutineHelper.launchCPU {
        effect.send(newEffect)
    }
}