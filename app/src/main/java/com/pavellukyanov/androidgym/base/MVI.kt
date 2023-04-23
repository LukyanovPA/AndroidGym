package com.pavellukyanov.androidgym.base

open class State(
    open val isLoading: Boolean = false
)

open class Action
open class Effect

sealed class Errors {
    data class ExpectedError(val code: Int, val message: String) : Errors()
    data class UndefinedError(val message: String) : Errors()
    data class OtherError(val error: Throwable) : Errors()
}