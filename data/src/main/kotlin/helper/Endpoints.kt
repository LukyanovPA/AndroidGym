package helper

import Constants.BASE_URL
import SecretValues
import android.annotation.SuppressLint
import org.koin.java.KoinJavaComponent.getKoin

internal object Endpoints {
    @SuppressLint("StaticFieldLeak")
    private val secret: SecretValues = getKoin().get()
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
    }
}