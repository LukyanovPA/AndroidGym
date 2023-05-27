package entity.questions

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val subcategoryId: Int,
    val subcategoryName: String,
    val question: String
)
