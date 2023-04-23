package dataSources.network

import base.BaseResponse
import entity.questions.Category
import helper.Endpoints
import helper.asData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal interface NetworkQuestions {
    suspend fun getAllCategories(): List<Category>
}

internal class NetworkQuestionsDataSource(
    private val httpClient: HttpClient
) : NetworkQuestions {
    override suspend fun getAllCategories(): List<Category> =
        httpClient.get {
            url { path(Endpoints.Questions.getAllCategory) }
            contentType(ContentType.Application.Json)
        }.body<BaseResponse<List<Category>>>().asData()
}