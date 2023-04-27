package dto

import database.entity.CacheEntity
import kotlinx.serialization.Serializable

@Serializable
data class CacheDto(
    val point: CachePoint,
    val update: Long
)

enum class CachePoint { CATEGORY, SUBCATEGORY, QUESTIONS }

internal fun CacheDto.map(): CacheEntity =
    CacheEntity(
        point = point.name,
        update = update
    )
