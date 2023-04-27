package base

import CoroutineHelper
import dataSources.local.LocalCache
import dataSources.network.NetworkCache
import dto.CachePoint
import dto.map
import helper.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.koin.java.KoinJavaComponent.inject

internal class CacheHelper {
    private val networkMonitor by inject<NetworkMonitor>(NetworkMonitor::class.java)
    private val localCache by inject<LocalCache>(LocalCache::class.java)
    private val networkCache by inject<NetworkCache>(NetworkCache::class.java)
    private val coroutineHelper = CoroutineHelper(GlobalScope)

    fun checkUpdates(point: CachePoint, isUpdate: suspend CoroutineScope.(Boolean) -> Unit) = coroutineHelper.launchIO {
        if (networkMonitor.isNetworkAvailable()) {
            val local = localCache.lastUpdate(point = point)
            val network = networkCache.lastUpdate(point = point).map()

            if (local == null) {
                localCache.insert(cacheEntity = network)
                coroutineHelper.launchIO { isUpdate(true) }
            } else {
                if (local.update != network.update) {
                    coroutineHelper.launchIO { isUpdate(true) }
                    localCache.update(cacheEntity = network)
                } else {
                    coroutineHelper.launchIO { isUpdate(false) }
                }
            }
        } else {
            coroutineHelper.launchIO { isUpdate(false) }
        }
    }
}