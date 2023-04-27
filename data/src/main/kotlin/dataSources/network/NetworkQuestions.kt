package dataSources.network

import base.BaseResponse
import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import helper.Endpoints
import helper.asData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

internal interface NetworkQuestions {
    suspend fun getAllCategories(): List<CategoryEntity>

    suspend fun getAllSubcategories(): List<SubcategoryEntity>

    suspend fun getAllQuestions(): List<QuestionEntity>

    suspend fun getAllAnswers(): List<AnswerEntity>
}

internal class NetworkQuestionsDataSource(
    private val httpClient: HttpClient
) : NetworkQuestions {
    override suspend fun getAllCategories(): List<CategoryEntity> =
        httpClient.get {
            url { path(Endpoints.Questions.getAllCategory) }
        }.body<BaseResponse<List<CategoryEntity>>>().asData()

    override suspend fun getAllSubcategories(): List<SubcategoryEntity> =
        httpClient.get {
            url { path(Endpoints.Questions.getAllSubcategories) }
        }.body<BaseResponse<List<SubcategoryEntity>>>().asData()

    override suspend fun getAllQuestions(): List<QuestionEntity> =
        httpClient.get {
            url { path(Endpoints.Questions.getAllQuestions) }
        }.body<BaseResponse<List<QuestionEntity>>>().asData()

    override suspend fun getAllAnswers(): List<AnswerEntity> =
        httpClient.get {
            url { path(Endpoints.Questions.getAllAnswers) }
        }.body<BaseResponse<List<AnswerEntity>>>().asData()
}