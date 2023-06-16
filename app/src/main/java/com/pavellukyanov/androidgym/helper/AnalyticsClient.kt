package com.pavellukyanov.androidgym.helper

import com.yandex.metrica.YandexMetrica

object AnalyticsClient {
    private const val screenName = "screen_name"
    private const val screenOpen = "screen_view"
    private const val eventOpen = "event"

    fun trackScreen(screen: String) {
        val keyValue = hashMapOf(screenName to screen)
        trackYandex(screenOpen, keyValue.toMap())
    }

    fun trackEvent(screen: String, event: String) {
        val keyValue = hashMapOf(screen to event)
        trackYandex(eventOpen, keyValue)
    }

    private fun trackYandex(event: String, keyValue: Map<String, String> = emptyMap()) {
        YandexMetrica.reportEvent(event, keyValue.toMap())
    }

    object ScreenNames {
        const val MAIN = "main_screen"
        const val ANSWER = "answer_screen"
        const val MAIN_MENU = "main_menu"
        const val FAVOURITES = "favourites"
    }

    object Events {
        const val SEARCH = "on_search"
        const val CLICK_CATEGORY = "click_category: "
        const val CLICK_SUBCATEGORY = "click_subcategory: "
        const val CLICK_MENU = "on_menu"
        const val QUESTION = "view_question: "
        const val FAVOURITES = "on_favourites"
        const val COMMENT = "on_comment_click"
        const val SEND_COMMENT = "on_send_comment"
        const val CLICK_FAVOURITES = "click_favourites"
    }
}