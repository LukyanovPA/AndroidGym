package base

import CoroutineHelper
import dataSources.local.LocalCache
import dataSources.local.LocalQuestions
import dataSources.network.NetworkCache
import dataSources.network.NetworkQuestions
import dto.CachePoint
import dto.map
import error.InternetConnectionException
import helper.NetworkMonitor
import kotlinx.coroutines.launch

internal class CacheHelper(
    private val networkMonitor: NetworkMonitor,
    private val localCache: LocalCache,
    private val networkCache: NetworkCache,
    private val localQuestions: LocalQuestions,
    private val networkQuestions: NetworkQuestions
) {
    private val scope = CoroutineHelper()

    suspend fun checkUpdates(point: CachePoint) = scope.launchIO {
        if (networkMonitor.isNetworkAvailable()) {
            val local = localCache.lastUpdate(point = point)
            val network = networkCache.lastUpdate(point = point).map()

            if (local == null) {
                launch {
                    localCache.insert(cacheEntity = network)
                    update(point = point)
                }
            } else {
                if (local.update != network.update) {
                    launch {
                        update(point = point)
                        localCache.update(cacheEntity = network)
                    }
                }
            }
        } else {
            throw InternetConnectionException(mess = "Отсутствует интернет соединение")
        }
    }

    private suspend fun update(point: CachePoint) {
        when (point) {
            CachePoint.CATEGORY -> updateCategoryCache()
            CachePoint.SUBCATEGORY -> updateSubcategoriesCache()
            CachePoint.QUESTIONS -> updateQuestionsCache()
            CachePoint.ANSWERS -> updateAnswersCache()
        }
    }

    private suspend fun updateCategoryCache() {
        localQuestions.insertCategories(
            categories = networkQuestions.getAllCategories()
        )
    }

    private suspend fun updateSubcategoriesCache() {
        localQuestions.insertSubcategories(
            subcategories = networkQuestions.getAllSubcategories()
        )
    }

    private suspend fun updateQuestionsCache() {
        localQuestions.insertQuestions(
            questions = networkQuestions.getAllQuestions()
        )
    }

    private suspend fun updateAnswersCache() {
        localQuestions.insertAnswers(
            answers = networkQuestions.getAllAnswers()
        )
    }
}