package useCase.questions

import entity.questions.MainItems
import helper.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import repository.QuestionRepository

interface GetAllCategories : suspend () -> Flow<List<MainItems>>

internal class GetAllCategoriesImpl(
    private val repo: QuestionRepository
) : GetAllCategories {

    override suspend fun invoke(): Flow<List<MainItems>> =
        combine(
            repo.getAllCategories(),
            repo.getAllSubcategories(),
            repo.getAllQuestions()
        ) { categories, subcategories, questions ->
            categories.map { category ->
                category.map(
                    subcategories.filter { it.categoryId == category.id }
                        .map { sub ->
                            sub.map(questions.filter { it.subcategoryId == sub.id }.map { it.map() })
                        }
                )
            }
        }
            .map { list -> MainItems.CategoryItem.map(list) }
            .flowOn(Dispatchers.Default)
}