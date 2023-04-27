package dataSources.local

import database.dao.CategoryDao
import database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

internal interface LocalQuestions {
    suspend fun getAllCategories(): Flow<List<CategoryEntity>>

    suspend fun insertCategories(categories: List<CategoryEntity>)
}

internal class LocalQuestionsDataSource(
    private val dao: CategoryDao
) : LocalQuestions {
    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        dao.getAll()

    override suspend fun insertCategories(categories: List<CategoryEntity>) {
        dao.insert(newList = categories)
    }
}