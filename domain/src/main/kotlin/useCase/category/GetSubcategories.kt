package useCase.category

import entity.main.MainItems
import ext.CPU
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetSubcategories : suspend (String, Int) -> Flow<List<MainItems>>

internal class GetSubcategoriesImpl(
    private val repo: QuestionRepository
) : GetSubcategories {

    override suspend fun invoke(query: String, categoryId: Int): Flow<List<MainItems>> =
        repo.getSubcategoriesByCategoryId(categoryId = categoryId)
            .map { list -> list.filter { it.name.contains(query, ignoreCase = true) }.map { it.map() } }
            .map { filteredList ->
                mutableListOf<MainItems>().apply {
                    if (filteredList.isEmpty()) add(MainItems.NotFoundItem) else addAll(MainItems.SubcategoryItem.map(filteredList))
                }
            }
            .CPU()
}