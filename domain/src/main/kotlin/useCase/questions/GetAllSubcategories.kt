package useCase.questions

import entity.questions.Subcategory
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAllSubcategories : suspend (Int) -> Flow<List<Subcategory>>

internal class GetAllSubcategoriesImpl(
    private val repository: QuestionRepository
) : GetAllSubcategories {
    override suspend fun invoke(categoryId: Int): Flow<List<Subcategory>> =
        repository.getAllSubcategories(categoryId = categoryId)
            .map { list -> list.map { it.map() } }
}