package useCase.questions

import entity.questions.MainItems
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAllCategories : suspend () -> Flow<List<MainItems>>

internal class GetAllCategoriesImpl(
    private val repo: QuestionRepository
) : GetAllCategories {
    override suspend fun invoke(): Flow<List<MainItems>> =
        repo.getAllCategories()
            .map { list -> MainItems.CategoryItem.map(list.map { it.map() }) }
}