package entity.questions

import java.util.Random

sealed class MainItems {
    open val id: Int = Random().nextInt()

    data class SubcategoryItem(val subcategory: Subcategory) : MainItems() {
        companion object {
            fun map(subcategories: List<Subcategory>): List<SubcategoryItem> =
                subcategories.map { SubcategoryItem(subcategory = it) }
        }
    }

    data class QuestionItem(val question: Question) : MainItems() {
        companion object {
            fun map(questions: List<Question>): List<QuestionItem> =
                questions.map { QuestionItem(question = it) }
        }
    }

    object NotFoundItem : MainItems()

    object Loading : MainItems()
}
