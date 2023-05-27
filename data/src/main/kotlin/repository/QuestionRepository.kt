package repository

import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    suspend fun getAllCategories(): Flow<List<CategoryEntity>>
    suspend fun getAllSubcategories(): Flow<List<SubcategoryEntity>>
    suspend fun getAllQuestions(): Flow<List<QuestionEntity>>
    suspend fun getAnswer(questionId: Int): Flow<AnswerEntity>
    suspend fun setFavouritesState(answerId: Int, state: Boolean)
}