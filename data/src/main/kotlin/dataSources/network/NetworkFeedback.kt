package dataSources.network

import base.BaseResponse
import dto.CreateAnswerFeedbackRequest
import helper.Endpoints
import helper.asData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

internal interface NetworkFeedback {
    suspend fun createAnswerComment(request: CreateAnswerFeedbackRequest)
}

internal class NetworkFeedbackImpl(
    private val httpClient: HttpClient
) : NetworkFeedback {
    override suspend fun createAnswerComment(request: CreateAnswerFeedbackRequest) =
        httpClient.post {
            url { path(Endpoints.AnswerFeedback.create) }
            setBody(request)
        }.body<BaseResponse<Unit>>().asData()
}