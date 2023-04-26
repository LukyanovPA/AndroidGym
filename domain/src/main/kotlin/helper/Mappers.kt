package helper

import database.entity.CategoryEntity
import entity.questions.Category

fun CategoryEntity.map(): Category =
    Category(
        id = id,
        name = name,
        questionsCount = questionsCount
    )