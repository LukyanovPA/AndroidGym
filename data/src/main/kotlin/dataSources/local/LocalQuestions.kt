package dataSources.local

import database.LocalDatabase
import entity.questions.Category
import kotlinx.coroutines.flow.Flow

internal interface LocalQuestions {
    suspend fun getAllCategories(): Flow<List<Category>>

    suspend fun insertCategories(categories: List<Category>)
}

internal class LocalQuestionsDataSource(
    private val db: LocalDatabase
) : LocalQuestions {
    override suspend fun getAllCategories(): Flow<List<Category>> =
        db.category().getAll()

    override suspend fun insertCategories(categories: List<Category>) {
        db.category().insert(newList = categories)
    }
}