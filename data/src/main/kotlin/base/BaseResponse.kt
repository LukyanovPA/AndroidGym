package base

import kotlinx.serialization.Serializable

@Serializable
internal data class BaseResponse<D>(
    val success: Boolean,
    val data: D? = null,
    val errors: HashMap<Int, String>? = null
)
