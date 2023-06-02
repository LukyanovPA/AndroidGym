package repository

interface FeedbackRepository {
    suspend fun createAnswerComment(answerId: Int, comment: String)
}