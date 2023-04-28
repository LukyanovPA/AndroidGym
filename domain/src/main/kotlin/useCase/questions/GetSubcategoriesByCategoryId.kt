package useCase.questions

import entity.questions.MainItems
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetSubcategoriesByCategoryId : suspend (Int) -> Flow<List<MainItems>>

internal class GetSubcategoriesByCategoryIdImpl(
    private val repository: QuestionRepository
) : GetSubcategoriesByCategoryId {
    override suspend fun invoke(categoryId: Int): Flow<List<MainItems>> =
        repository.getAllSubcategories(categoryId = categoryId)
            .map { list -> MainItems.SubcategoryItem.map(list.map { it.map() }) }
}