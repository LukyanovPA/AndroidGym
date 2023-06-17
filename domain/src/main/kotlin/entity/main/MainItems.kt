package entity.main

import entity.models.Category
import entity.models.Question
import entity.models.Subcategory
import java.util.Random

sealed class MainItems {
    open val id: Int = Random().nextInt()

    data class CategoryItem(val category: Category) : MainItems() {
        companion object {
            fun map(categories: List<Category>): List<CategoryItem> =
                categories.map { CategoryItem(category = it) }
        }
    }

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
