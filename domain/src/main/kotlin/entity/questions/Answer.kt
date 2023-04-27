package entity.questions

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val subcategoryId: Int,
    val subcategoryName: String,
    val questionId: Int,
    val question: String,
    val answer: String
)
