package repository.impl

import base.CacheHelper
import dataSources.local.LocalQuestions
import dataSources.network.NetworkQuestions
import database.entity.CategoryEntity
import dto.CachePoint
import kotlinx.coroutines.flow.Flow
import repository.QuestionRepository

internal class QuestionRepositoryImpl(
    private val localQuestions: LocalQuestions,
    private val networkQuestions: NetworkQuestions,
) : QuestionRepository {
    init {
        CacheHelper().checkUpdates(point = CachePoint.CATEGORY) { isUpdate ->
            if (isUpdate) updateCategoryCache()
        }
    }

    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        localQuestions.getAllCategories()

    private suspend fun updateCategoryCache() {
        localQuestions.insertCategories(
            categories = networkQuestions.getAllCategories()
        )
    }
}