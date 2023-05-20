import kotlinx.coroutines.flow.Flow

interface ObjectStoraje<T : Any> {
    fun set(value: T)

    fun get(): Flow<T>

    fun hasValue(): Boolean
}