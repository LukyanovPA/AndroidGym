package base

import CoroutineHelper
import dataSources.local.LocalCache
import dataSources.local.LocalQuestions
import dataSources.network.NetworkQuestions
import dataSources.network.NetworkTimestamp
import dto.CachePoint
import dto.map
import error.InternetConnectionException
import helper.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal class CacheHelper(
    private val networkMonitor: NetworkMonitor,
    private val localCache: LocalCache,
    private val networkTimestamp: NetworkTimestamp,
    private val localQuestions: LocalQuestions,
    private val networkQuestions: NetworkQuestions
) {
    private val job = SupervisorJob()
    private val _scope = CoroutineScope(Dispatchers.Default + job)

    private val scope = CoroutineHelper(_scope)

    suspend fun checkUpdates(point: CachePoint) = scope.launchIO {
        if (networkMonitor.isNetworkAvailable()) {
            val local = localCache.getLastTimestamp(point = point)
            val network = networkTimestamp.lastUpdate(point = point).map()

            if (local == null) {
                localCache.insert(cacheEntity = network)
                update(point = point)
            } else {
                if (local.update != network.update) {
                    update(point = point)
                    localCache.update(cacheEntity = network)
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
        localQuestions.deleteAllAnswers()
        localQuestions.insertAnswers(
            answers = networkQuestions.getAllAnswers()
        )
    }
}