package repository

import database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    suspend fun getAllCategories(): Flow<List<CategoryEntity>>
}