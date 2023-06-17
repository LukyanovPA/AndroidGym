package useCase.category

import entity.main.MainItems
import ext.CPU
import helper.IdStorage
import helper.map
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import repository.QuestionRepository

interface GetSubcategories : suspend (String) -> Flow<List<MainItems>>

internal class GetSubcategoriesImpl(
    private val repo: QuestionRepository,
    private val idStorage: IdStorage
) : GetSubcategories {
    @OptIn(FlowPreview::class)
    override suspend fun invoke(query: String): Flow<List<MainItems>> =
        idStorage.get()
            .onStart { listOf(MainItems.Loading) }
            .flatMapMerge { id ->
                repo.getSubcategoriesByCategoryId(categoryId = id)
                    .map { list -> list.filter { it.name.contains(query, ignoreCase = true) }.map { it.map() } }
            }
            .map { filteredList ->
                mutableListOf<MainItems>().apply {
                    if (filteredList.isEmpty()) add(MainItems.NotFoundItem) else addAll(MainItems.SubcategoryItem.map(filteredList))
                }
            }
            .CPU()
}