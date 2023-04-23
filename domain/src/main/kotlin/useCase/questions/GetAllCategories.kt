package useCase.questions

import entity.questions.Category
import kotlinx.coroutines.flow.Flow
import repository.QuestionRepository

interface GetAllCategories : suspend () -> Flow<List<Category>>

internal class GetAllCategoriesImpl(
    private val repo: QuestionRepository
) : GetAllCategories {
    override suspend fun invoke(): Flow<List<Category>> = repo.getAllCategories()
}