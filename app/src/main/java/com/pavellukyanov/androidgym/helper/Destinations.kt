package com.pavellukyanov.androidgym.helper

object Destinations {
    private const val ui = "ui/feature/"

    object Arguments {
        const val CATEGORY_NAME_ARG = "categoryName"
        const val CATEGORY_ID_ARG = "categoryId"
        const val SUBCATEGORY_NAME_ARG = "subcategoryName"
        const val SUBCATEGORY_ID_ARG = "subcategoryId"
        const val QUESTION_ID_ARG = "questionId"
    }

    object Error {
        const val ERROR = ui + "error"
    }

    object Main {
        const val MAIN = ui + "main"
    }

    object Answer {
        fun nav(questionId: Int): String = ui + "answer/$questionId"
        const val ANSWER = ui + "answer/{${Arguments.QUESTION_ID_ARG}}"
    }

    object Favourites {
        const val FAVOURITES = ui + "favourites"
    }

    object Category {
        fun nav(categoryName: String, categoryId: Int): String = ui + "category/$categoryName/$categoryId"
        const val CATEGORY_ROUTE = ui + "category/{${Arguments.CATEGORY_NAME_ARG}}/{${Arguments.CATEGORY_ID_ARG}}"
    }

    object Subcategory {
        fun nav(subcategoryName: String, subcategoryId: Int): String = ui + "subcategory/$subcategoryName/$subcategoryId"
        const val SUBCATEGORY_ROUTE = ui + "subcategory/{${Arguments.SUBCATEGORY_NAME_ARG}}/{${Arguments.SUBCATEGORY_ID_ARG}}"
    }
}