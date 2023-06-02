package repository.impl

import dataSources.network.NetworkFeedback
import dto.CreateAnswerFeedbackRequest
import repository.FeedbackRepository

internal class FeedbackRepositoryImpl(
    private val networkFeedback: NetworkFeedback
) : FeedbackRepository {
    override suspend fun createAnswerComment(answerId: Int, comment: String) =
        networkFeedback.createAnswerComment(
            CreateAnswerFeedbackRequest(
                answerId = answerId,
                comment = comment
            )
        )
}