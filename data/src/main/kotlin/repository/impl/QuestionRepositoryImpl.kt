package repository.impl

import base.CacheHelper
import dataSources.local.LocalQuestions
import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

internal class QuestionRepositoryImpl(
    private val localQuestions: LocalQuestions
) : QuestionRepository {

    init {
        CacheHelper().checkUpdates()
    }

    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        localQuestions.getAllCategories()

    override suspend fun getAllSubcategories(categoryId: Int): Flow<List<SubcategoryEntity>> =
        localQuestions.getAllSubcategoriesByCategoryId(categoryId = categoryId)

    override suspend fun getAllQuestions(subcategoryId: Int): Flow<List<QuestionEntity>> =
        localQuestions.getAllQuestionsBySubcategoryId(subcategoryId = subcategoryId)

    @OptIn(FlowPreview::class)
    override suspend fun getAnswer(questionId: Int): Flow<AnswerEntity> =
        localQuestions.getAnswers(questionId = questionId)
            .flatMapMerge { list ->
                flow {
                    if (list.isNotEmpty()) emit(list.first())
                }
            }

    override suspend fun searchSubcategories(query: String): Flow<List<SubcategoryEntity>> =
        localQuestions.getAllSubcategories().map { list ->
            list.filter { it.name.contains(query, ignoreCase = true) }
        }

    override suspend fun searchQuestions(query: String): Flow<List<QuestionEntity>> =
        localQuestions.getAllQuestions().map { list ->
            list.filter { it.question.contains(query, ignoreCase = true) }
        }
}