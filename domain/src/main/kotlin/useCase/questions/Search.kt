package useCase.questions

import entity.questions.MainItems
import ext.CPU
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
            repo.getAllSubcategories(),
            repo.getAllQuestions()
        ) { sub, questions ->
            val filteredSub = sub.filter { it.name.contains(query, ignoreCase = true) }
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
            .CPU()
}