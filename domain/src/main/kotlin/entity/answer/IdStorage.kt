package entity.answer

import Constants.INT_MINUS_ONE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class IdStorage {
    private val storage = MutableStateFlow(INT_MINUS_ONE)

    suspend fun set(value: Int) {
        storage.emit(value)
    }

    fun get(): Flow<Int> = storage
}