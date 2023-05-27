package helper

internal object Endpoints {
    private const val API = ":8080/api/"
    const val BASE_URL_DEVICE = "http://192.168.1.65$API"

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
}