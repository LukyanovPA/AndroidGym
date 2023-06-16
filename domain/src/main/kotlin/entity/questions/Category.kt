package entity.questions

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val questionsCount: Int
)
