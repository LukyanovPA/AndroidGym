package dto

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateAnswerFeedbackRequest(
    val answerId: Int,
    val comment: String
)
