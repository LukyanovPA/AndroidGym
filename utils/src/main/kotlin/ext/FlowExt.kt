package ext

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

fun <T> Flow<T>.IO(): Flow<T> =
    flowOn(Dispatchers.IO)

fun <T> Flow<T>.CPU(): Flow<T> =
    flowOn(Dispatchers.Default)