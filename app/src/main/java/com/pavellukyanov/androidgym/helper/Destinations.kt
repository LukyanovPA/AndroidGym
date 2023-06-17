package com.pavellukyanov.androidgym.helper

object Destinations {
    private const val ui = "ui/feature/"

    object Arguments {
        const val CATEGORY_ARG = "categoryName"
    }

    object Error {
        const val ERROR = ui + "error"
    }

    object Main {
        const val MAIN = ui + "main"
    }

    object Answer {
        const val ANSWER = ui + "answer"
    }

    object Favourites {
        const val FAVOURITES = ui + "favourites"
    }

    object Category {
        const val CATEGORY = ui + "category/"
        const val CATEGORY_ROUTE = ui + "category/{categoryName}"
    }
}