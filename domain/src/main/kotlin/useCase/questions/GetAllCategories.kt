package useCase.questions

import entity.questions.Category
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAllCategories : suspend () -> Flow<List<Category>>

internal class GetAllCategoriesImpl(
    private val repo: QuestionRepository
) : GetAllCategories {
    override suspend fun invoke(): Flow<List<Category>> =
        repo.getAllCategories()
            .map { cat -> cat.map { it.map() } }
}