package helper

import Constants.INT_ZERO
import database.entity.CategoryEntity
import entity.questions.Category

fun CategoryEntity.map(): Category =
    Category(
        id = id,
        name = name,
        questionsCount = questionsCount ?: INT_ZERO
    )