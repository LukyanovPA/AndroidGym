package repository.impl

import dataSources.network.NetworkAnswerFeedback
import dto.CreateAnswerFeedbackRequest
import repository.AnswerFeedbackRepository

internal class AnswerFeedbackRepositoryImpl(
    private val networkAnswerFeedback: NetworkAnswerFeedback
) : AnswerFeedbackRepository {
    override suspend fun invoke(answerId: Int, comment: String): Unit =
        networkAnswerFeedback(
            CreateAnswerFeedbackRequest(
                answerId = answerId,
                comment = comment
            )
        )
}