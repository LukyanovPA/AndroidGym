package repository.impl

import base.CacheHelper
import dataSources.local.LocalQuestions
import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import dto.CachePoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import repository.QuestionRepository

internal class QuestionRepositoryImpl(
    private val localQuestions: LocalQuestions
) : QuestionRepository {

    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        localQuestions.getAllCategories()
            .onStart { checkUpdates(point = CachePoint.CATEGORY) }
            .flowOn(Dispatchers.IO)

    override suspend fun getAllSubcategories(): Flow<List<SubcategoryEntity>> =
        localQuestions.getAllSubcategories()
            .onStart { checkUpdates(point = CachePoint.SUBCATEGORY) }
            .flowOn(Dispatchers.IO)

    override suspend fun getAllQuestions(): Flow<List<QuestionEntity>> =
        localQuestions.getAllQuestions()
            .onStart { checkUpdates(point = CachePoint.QUESTIONS) }
            .flowOn(Dispatchers.IO)

    @OptIn(FlowPreview::class)
    override suspend fun getAnswer(questionId: Int): Flow<AnswerEntity> =
        localQuestions.getAnswers(questionId = questionId)
            .onStart { checkUpdates(point = CachePoint.ANSWERS) }
            .flatMapMerge { list ->
                flow {
                    if (list.isNotEmpty()) emit(list.first())
                }
            }
            .flowOn(Dispatchers.IO)

    override suspend fun searchSubcategories(query: String): Flow<List<SubcategoryEntity>> =
        localQuestions.getAllSubcategories().map { list ->
            list.filter { it.name.contains(query, ignoreCase = true) }
        }
            .flowOn(Dispatchers.IO)

    override suspend fun searchQuestions(query: String): Flow<List<QuestionEntity>> =
        localQuestions.getAllQuestions().map { list ->
            list.filter { it.question.contains(query, ignoreCase = true) }
        }
            .flowOn(Dispatchers.IO)

    private suspend fun checkUpdates(point: CachePoint) {
        CacheHelper().checkUpdates(point = point)
    }
}