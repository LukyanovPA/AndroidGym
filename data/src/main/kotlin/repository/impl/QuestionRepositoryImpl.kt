package repository.impl

import dataSources.local.LocalQuestions
import dataSources.network.NetworkQuestions
import database.entity.CategoryEntity
import helper.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import repository.QuestionRepository

internal class QuestionRepositoryImpl(
    private val networkMonitor: NetworkMonitor,
    private val local: LocalQuestions,
    private val network: NetworkQuestions
) : QuestionRepository {
    init {
        runBlocking(Dispatchers.IO) { updateLocalCache() }
    }

    override suspend fun getAllCategories(): Flow<List<CategoryEntity>> =
        local.getAllCategories()

    private suspend fun updateLocalCache() {
        if (networkMonitor.isNetworkAvailable()) {
            local.insertCategories(
                categories = network.getAllCategories()
            )
        }
    }
}