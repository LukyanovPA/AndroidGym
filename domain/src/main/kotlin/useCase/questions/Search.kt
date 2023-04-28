package useCase.questions

import entity.questions.MainItems
import helper.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import repository.QuestionRepository

interface Search : suspend (String) -> Flow<List<MainItems>>

internal class SearchImpl(
    private val repo: QuestionRepository
) : Search {
    override suspend fun invoke(query: String): Flow<List<MainItems>> =
        combine(
            repo.searchSubcategories(query = query),
            repo.searchQuestions(query = query)
        ) { sub, question ->
            mutableListOf<MainItems>().apply {
                if (sub.isEmpty() && question.isEmpty()) {
                    add(MainItems.NotFoundItem)
                } else {
                    addAll(MainItems.SubcategoryItem.map(sub.map { it.map() }))
                    addAll(MainItems.QuestionItem.map(question.map { it.map() }))
                }
            }
        }
}