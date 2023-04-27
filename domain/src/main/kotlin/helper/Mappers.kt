package helper

import Constants.INT_ZERO
import database.entity.AnswerEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity
import entity.questions.Answer
import entity.questions.Category
import entity.questions.Question
import entity.questions.Subcategory

fun CategoryEntity.map(): Category =
    Category(
        id = id,
        name = name,
        questionsCount = questionsCount ?: INT_ZERO
    )

fun SubcategoryEntity.map(): Subcategory =
    Subcategory(
        id = id,
        categoryId = categoryId,
        categoryName = categoryName,
        name = name
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