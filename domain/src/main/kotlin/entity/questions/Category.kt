package entity.questions

import Constants.INT_ZERO
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val questionsCount: Int = INT_ZERO
)
