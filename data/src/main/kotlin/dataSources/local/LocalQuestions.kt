package dataSources.local

import database.LocalDatabase
import database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

internal interface LocalQuestions {
    suspend fun getAllCategories(): Flow<List<CategoryEntity>>

    suspend fun insertCategories(categories: List<CategoryEntity>)
}

internal class LocalQuestionsDataSource(
    private val db: LocalDatabase
) : LocalQuestions {
    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        db.category().getAll()

    override suspend fun insertCategories(categories: List<CategoryEntity>) {
        db.category().insert(newList = categories)
    }
}