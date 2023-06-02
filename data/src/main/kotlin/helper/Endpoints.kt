package helper

import Constants.BASE_URL
import SecretValues
import org.koin.java.KoinJavaComponent

internal object Endpoints {
    private val secret by KoinJavaComponent.inject<SecretValues>(SecretValues::class.java)
    val BASE_URL_DEVICE = secret.getValue(BASE_URL)

    object Questions {
        private const val question = "questions/"

        const val getAllCategory = question + "getAllCategories"
        const val getAllSubcategories = question + "getAllSubcategories"
        const val getAllQuestions = question + "getAllQuestions"
        const val getAllAnswers = question + "getAllAnswers"
    }

    object Cache {
        private const val cache = "cache/"

        const val lastUpdate = cache + "lastUpdate"
    }

    object AnswerFeedback {
        private const val answerFeedback = "answerFeedback/"

        const val create = answerFeedback + "create"
        const val getAll = answerFeedback + "getAll"
        const val changeStatus = answerFeedback + "changeStatus"
    }
}