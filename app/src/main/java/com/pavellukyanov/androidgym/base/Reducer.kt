package com.pavellukyanov.androidgym.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import error.ApiExceptions
import error.ErrorState
import error.ErrorStorage
import error.Errors
import error.InternetConnectionException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import timber.log.Timber

abstract class Reducer<STATE : State, ACTION : Action, EFFECT : Effect>(initState: STATE) : ViewModel() {
    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initState)
    private val errorStorage by KoinJavaComponent.inject<ErrorStorage>(ErrorStorage::class.java)
    private val errorHandler = CoroutineExceptionHandler { context, exception ->
        Timber.tag(TAG).e("Context: $context, Exception: $exception")
        handleError(exception)
    }

    val state: StateFlow<STATE> = _state.asStateFlow()
    val effect: Channel<EFFECT> = Channel()

    protected abstract suspend fun reduce(oldState: STATE, action: ACTION)

    fun sendAction(action: ACTION) = launchCPU {
        reduce(_state.value, action)
    }

    protected fun saveState(newState: STATE) = launchUI {
        _state.emit(newState)
    }

    protected fun sendEffect(newEffect: EFFECT) = launchUI {
        effect.send(newEffect)
    }

    private fun launchUI(action: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.Main, action)

    protected fun launchIO(action: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.IO, action)

    protected fun launchCPU(action: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.Default, action)

    private fun launch(dispatcher: CoroutineDispatcher, action: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(context = dispatcher + errorHandler, block = action)
    }

    private fun handleError(exception: Throwable) = launchCPU {
        errorStorage.onError.send(ErrorState.ShowError)
        when (exception) {
            is ApiExceptions.ExpectedException -> errorStorage.error.emit(
                Errors.ExpectedError(
                    code = exception.errorCode,
                    message = exception.errorMessage
                )
            )

            is ApiExceptions.UndefinedException -> errorStorage.error.emit(Errors.UndefinedError(message = exception.errorMessage))
            //TODO переделать на обработку отсутствия интернета
            is InternetConnectionException -> errorStorage.error.emit(Errors.UndefinedError(message = exception.message!!))
            else -> errorStorage.error.emit(Errors.OtherError(error = exception))
        }
    }

    companion object {
        private const val TAG = "ReducerError"
    }
}