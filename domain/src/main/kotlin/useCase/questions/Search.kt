package useCase.questions

import entity.questions.MainItems
import helper.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import repository.QuestionRepository

interface Search : suspend (String) -> Flow<List<MainItems>>

internal class SearchImpl(
    private val repo: QuestionRepository
) : Search {
    override suspend fun invoke(query: String): Flow<List<MainItems>> =
        combine(
            repo.getAllSubcategories(),
            repo.getAllQuestions()
        ) { sub, question ->
            mutableListOf<MainItems>().apply {
                if (sub.isEmpty() && question.isEmpty()) {
                    add(MainItems.NotFoundItem)
                } else {
                    addAll(
                        MainItems.SubcategoryItem.map(
                            sub
                                .filter { it.name.contains(query, ignoreCase = true) }
                                .map { s ->
                                    s.map(question.filter { it.subcategoryId == s.id }.map { it.map() })
                                }
                        )
                    )
                    addAll(
                        MainItems.QuestionItem.map(
                            question
                                .filter { it.question.contains(query, ignoreCase = true) }
                                .map { it.map() }
                        )
                    )
                }
            }
        }
            .flowOn(Dispatchers.Default)
}