package repository

import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    suspend fun getAllCategories(): Flow<List<CategoryEntity>>

    suspend fun getAllSubcategories(categoryId: Int): Flow<List<SubcategoryEntity>>

    suspend fun getAllQuestions(subcategoryId: Int): Flow<List<QuestionEntity>>

    suspend fun getAnswer(questionId: Int): Flow<AnswerEntity>

    suspend fun searchSubcategories(query: String): Flow<List<SubcategoryEntity>>

    suspend fun searchQuestions(query: String): Flow<List<QuestionEntity>>
}