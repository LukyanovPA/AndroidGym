package repository

import entity.questions.Category
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    suspend fun getAllCategories(): Flow<List<Category>>
}