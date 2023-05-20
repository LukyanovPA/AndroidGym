package helper

import Constants.INT_ZERO
import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import entity.answer.Answer
import entity.questions.Category
import entity.questions.Question
import entity.questions.Subcategory

fun CategoryEntity.map(subcategories: List<Subcategory>): Category =
    Category(
        id = id,
        name = name,
        questionsCount = questionsCount ?: INT_ZERO,
        subcategories = subcategories
    )

fun SubcategoryEntity.map(questions: List<Question>): Subcategory =
    Subcategory(
        id = id,
        categoryId = categoryId,
        categoryName = categoryName,
        name = name,
        questions = questions
    )

fun QuestionEntity.map(): Question =
    Question(
        id = id,
        categoryId = categoryId,
        categoryName = categoryName,
        subcategoryId = subcategoryId,
        subcategoryName = subcategoryName,
        question = question
    )

fun AnswerEntity.map(): Answer =
    Answer(
        id = id,
        categoryId = categoryId,
        categoryName = categoryName,
        subcategoryId = subcategoryId,
        subcategoryName = subcategoryName,
        questionId = questionId,
        question = question,
        answer = answer
    )