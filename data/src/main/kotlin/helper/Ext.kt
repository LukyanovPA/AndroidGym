package helper

import base.BaseResponse
import entity.error.ApiExceptions
import entity.error.HttpResponseCode

internal fun <D> BaseResponse<D>.asData(): D =
    if (success) {
        data!!
    } else {
        val key = errors!!.keys.first()
        throw when (key) {
            in HttpResponseCode.EXPECTED.errorCode -> ApiExceptions.ExpectedException(errorCode = key, errorMessage = errors[key]!!)
            in HttpResponseCode.UNDEFINED.errorCode -> ApiExceptions.UndefinedException(errorMessage = errors[key]!!)
            else -> IllegalStateException("Unexpected response")
        }
    }