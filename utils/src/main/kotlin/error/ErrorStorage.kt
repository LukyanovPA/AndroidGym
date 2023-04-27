package error

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

class ErrorStorage {
    val onError: Channel<ErrorState> = Channel()

    val error = MutableStateFlow<Errors?>(null)
}

sealed class ErrorState {
    object ShowError : ErrorState()
}