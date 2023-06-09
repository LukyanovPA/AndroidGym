package entity.models

import kotlinx.serialization.Serializable

@Serializable
data class Subcategory(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val name: String
)
