package error

sealed class ApiExceptions(override val message: String? = null) : Exception(message) {
    class ExpectedException(val errorCode: Int, val errorMessage: String) : ApiExceptions()
    class UndefinedException(val errorMessage: String) : ApiExceptions(errorMessage)
}

class InternetConnectionException(private val mess: String?) : java.lang.Exception(mess)

enum class HttpResponseCode(val errorCode: IntRange) {
    EXPECTED(10000..19000),
    UNDEFINED(90000..90000)
}

sealed class Errors {
    data class ExpectedError(val code: Int, val message: String) : Errors()
    data class UndefinedError(val message: String) : Errors()
    data class OtherError(val error: Throwable) : Errors()
}
