package dataSources.local

import database.LocalDatabase
import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow

internal interface LocalQuestions {
    suspend fun getAllCategories(): Flow<List<CategoryEntity>>

    suspend fun insertCategories(categories: List<CategoryEntity>)

    suspend fun getAllSubcategories(categoryId: Int): Flow<List<SubcategoryEntity>>

    suspend fun insertSubcategories(subcategories: List<SubcategoryEntity>)

    suspend fun getAllQuestions(subcategoryId: Int): Flow<List<QuestionEntity>>

    suspend fun insertQuestions(questions: List<QuestionEntity>)

    suspend fun getAnswers(questionId: Int): Flow<List<AnswerEntity>>

    suspend fun insertAnswers(answers: List<AnswerEntity>)
}

internal class LocalQuestionsDataSource(
    private val db: LocalDatabase
) : LocalQuestions {
    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        db.category().getAll()

    override suspend fun insertCategories(categories: List<CategoryEntity>) {
        db.category().insert(newList = categories)
    }

    override suspend fun getAllSubcategories(categoryId: Int): Flow<List<SubcategoryEntity>> =
        db.subcategories().getAll(categoryId = categoryId)

    override suspend fun insertSubcategories(subcategories: List<SubcategoryEntity>) {
        db.subcategories().insert(subcategories = subcategories)
    }

    override suspend fun getAllQuestions(subcategoryId: Int): Flow<List<QuestionEntity>> =
        db.questions().getAll(subcategoryId = subcategoryId)

    override suspend fun insertQuestions(questions: List<QuestionEntity>) {
        db.questions().insert(questions = questions)
    }

    override suspend fun getAnswers(questionId: Int): Flow<List<AnswerEntity>> =
        db.answers().getAll(questionId = questionId)

    override suspend fun insertAnswers(answers: List<AnswerEntity>) {
        db.answers().insert(answers = answers)
    }
}