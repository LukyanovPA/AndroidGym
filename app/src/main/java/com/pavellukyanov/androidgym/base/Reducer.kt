package com.pavellukyanov.androidgym.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import error.ApiExceptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

abstract class Reducer<STATE : State, ACTION : Action, EFFECT : Effect>(initState: STATE) : ViewModel() {
    private val errorStorage by inject<ErrorStorage>(ErrorStorage::class.java)
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initState)

    val state: Flow<STATE> = _state
    val effect: Channel<EFFECT> = Channel()

    protected abstract suspend fun reduce(oldState: STATE, action: ACTION)

    fun sendAction(action: ACTION) = launchCPU {
        reduce(_state.value, action)
    }

    protected fun saveState(newState: STATE) = launchCPU {
        _state.emit(newState)
    }

    protected fun sendEffect(newEffect: EFFECT) = launchCPU {
        effect.send(newEffect)
    }

    protected fun launchIO(action: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.IO, action)

    private fun launchCPU(action: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.Default, action)

    private fun launch(dispatcher: CoroutineDispatcher, action: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(dispatcher) {
            try {
                action()
            } catch (e: Throwable) {
                Timber.tag(TAG).e(e)
                errorStorage.onError.send(ErrorState.ShowError)
                when (e) {
                    is ApiExceptions.ExpectedException -> errorStorage.error.emit(
                        Errors.ExpectedError(
                            code = e.errorCode,
                            message = e.errorMessage
                        )
                    )
                    is ApiExceptions.UndefinedException -> errorStorage.error.emit(Errors.UndefinedError(message = e.errorMessage))
                    else -> errorStorage.error.emit(Errors.OtherError(error = e))
                }
            }
        }
    }

    companion object {
        private const val TAG = "ViewModelScopeError"
    }
}