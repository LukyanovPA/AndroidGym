import error.ApiExceptions
import error.ErrorState
import error.ErrorStorage
import error.Errors
import error.InternetConnectionException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class CoroutineHelper(private val scope: CoroutineScope) {
    private val errorStorage by inject<ErrorStorage>(ErrorStorage::class.java)

    fun launchUI(action: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.Main, action)

    fun launchIO(action: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.IO, action)

    fun launchCPU(action: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.Default, action)

    private fun launch(dispatcher: CoroutineDispatcher, action: suspend CoroutineScope.() -> Unit) {
        scope.launch(dispatcher) {
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
                    //TODO переделать на обработку отсутствия интернета
                    is InternetConnectionException -> errorStorage.error.emit(Errors.UndefinedError(message = e.message!!))
                    else -> errorStorage.error.emit(Errors.OtherError(error = e))
                }
            }
        }
    }

    companion object {
        private const val TAG = "CoroutineHelperError"
    }
}