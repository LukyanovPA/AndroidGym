package helper

import base.BaseResponse
import error.ApiExceptions
import error.HttpResponseCode
import timber.log.Timber

internal fun <D> BaseResponse<D>.asData(): D =
    if (success) {
        data!!
    } else {
        val key = errors!!.keys.first()
        Timber.tag(TAG).e(errors[key]!!)
        throw when (key) {
            in HttpResponseCode.EXPECTED.errorCode -> ApiExceptions.ExpectedException(errorCode = key, errorMessage = errors[key]!!)
            in HttpResponseCode.UNDEFINED.errorCode -> ApiExceptions.UndefinedException(errorMessage = errors[key]!!)
            else -> IllegalStateException("Unexpected response")
        }
    }

private const val TAG = "KtorError"