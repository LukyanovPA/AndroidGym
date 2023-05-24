package useCase.questions

import Constants.INT_ZERO
import entity.questions.Category
import ext.CPU
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import repository.QuestionRepository

interface GetCategories : suspend () -> Flow<List<Category>>

internal class GetCategoriesImpl(
    private val repo: QuestionRepository
) : GetCategories {
    override suspend fun invoke(): Flow<List<Category>> =
        combine(
            repo.getAllCategories(),
            repo.getAllSubcategories(),
            repo.getAllQuestions()
        ) { cat, sub, qus ->
            cat.mapIndexed { index, category ->
                category.map(
                    subcategories = sub
                        .filter { it.categoryId == category.id }
                        .map { subcategory ->
                            subcategory.map(
                                questions = qus
                                    .filter { it.subcategoryId == subcategory.id }
                                    .map { it.map() }
                            )
                        },
                    isExpend = index == INT_ZERO
                )
            }
        }.CPU()
}