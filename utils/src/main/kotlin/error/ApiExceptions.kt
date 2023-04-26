package error

sealed class ApiExceptions(override val message: String? = null) : Exception(message) {
    class ExpectedException(val errorCode: Int, val errorMessage: String) : ApiExceptions()
    class UndefinedException(val errorMessage: String) : ApiExceptions(errorMessage)
}

enum class HttpResponseCode(val errorCode: IntRange) {
    EXPECTED(10000..19000),
    UNDEFINED(90000..90000)
}
