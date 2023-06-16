package repository.impl

import base.CacheHelper
import dataSources.local.LocalQuestions
import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import dto.CachePoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import repository.QuestionRepository

internal class QuestionRepositoryImpl(
    private val localQuestions: LocalQuestions,
    private val cacheHelper: CacheHelper
) : QuestionRepository {
    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        localQuestions.getAllCategories()
            .onStart { checkUpdates(point = CachePoint.CATEGORY) }

    override suspend fun getAllSubcategories(): Flow<List<SubcategoryEntity>> =
        localQuestions.getAllSubcategories()
            .onStart { checkUpdates(point = CachePoint.SUBCATEGORY) }

    override suspend fun getAllQuestions(): Flow<List<QuestionEntity>> =
        localQuestions.getAllQuestions()
            .onStart { checkUpdates(point = CachePoint.QUESTIONS) }

    @OptIn(FlowPreview::class)
    override suspend fun getAnswer(questionId: Int): Flow<AnswerEntity> =
        localQuestions.getAnswers(questionId = questionId)
            .onStart { checkUpdates(point = CachePoint.ANSWERS) }
            .flatMapMerge { list ->
                flow {
                    if (list.isNotEmpty()) emit(list.first())
                }
            }

    override suspend fun setFavouritesState(answerId: Int, state: Boolean) {
        val updatedAnswer = localQuestions.getAnswer(id = answerId).copy(isFavourites = state)
        localQuestions.setFavouritesState(answer = updatedAnswer)
    }

    override suspend fun getAllFavouritesAnswers(): Flow<List<AnswerEntity>> =
        localQuestions.getAllFavouritesAnswers()

    private suspend fun checkUpdates(point: CachePoint) {
        cacheHelper.checkUpdates(point = point)
    }
}