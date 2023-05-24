package useCase.questions

import entity.questions.MainItems
import ext.CPU
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import repository.QuestionRepository

interface GetAllCategories : suspend (String) -> Flow<List<MainItems>>

internal class GetAllCategoriesImpl(
    private val repo: QuestionRepository
) : GetAllCategories {

    override suspend operator fun invoke(query: String): Flow<List<MainItems>> =
        combine(
            repo.getAllCategories(),
            repo.getAllSubcategories(),
            repo.getAllQuestions()
        ) { categories, subcategories, questions ->
            if (query.isEmpty()) {
                mutableListOf<MainItems>().apply {
                    addAll(
                        MainItems.CategoryItem.map(categories.map { category ->
                            category.map(
                                subcategories.filter { it.categoryId == category.id }
                                    .map { sub ->
                                        sub.map(
                                            questions.filter { it.subcategoryId == sub.id }
                                                .map { it.map() }
                                        )
                                    }
                            )
                        })
                    )
                }
            } else {
                val filteredSub = subcategories.filter { it.name.contains(query, ignoreCase = true) }
                val filteredQuestions = questions.filter { it.question.contains(query, ignoreCase = true) }

                mutableListOf<MainItems>().apply {
                    if (filteredSub.isEmpty() && filteredQuestions.isEmpty()) {
                        add(MainItems.NotFoundItem)
                    } else {
                        addAll(
                            MainItems.SubcategoryItem.map(
                                filteredSub.map { s ->
                                    s.map(questions.filter { it.subcategoryId == s.id }.map { it.map() })
                                }
                            )
                        )
                        addAll(
                            MainItems.QuestionItem.map(
                                filteredQuestions.map { it.map() }
                            )
                        )
                    }
                }
            }
        }
            .CPU()
}