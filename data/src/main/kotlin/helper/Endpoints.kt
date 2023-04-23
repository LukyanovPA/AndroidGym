package helper

internal object Endpoints {
    const val BASE_URL_DEBUG = "http://10.0.2.2:8080/api/"

    object Questions {
        private const val question = "questions/"

        const val getAllCategory = question + "getAllCategories"
    }
}