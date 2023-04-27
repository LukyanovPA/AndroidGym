package dto

import database.entity.CacheEntity
import kotlinx.serialization.Serializable

@Serializable
internal data class CacheDto(
    val point: CachePoint,
    val update: Long
)

internal enum class CachePoint { CATEGORY, SUBCATEGORY, QUESTIONS, ANSWERS }

internal fun CacheDto.map(): CacheEntity =
    CacheEntity(
        point = point.name,
        update = update
    )
