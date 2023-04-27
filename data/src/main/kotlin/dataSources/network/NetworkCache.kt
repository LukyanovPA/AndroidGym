package dataSources.network

import base.BaseResponse
import dto.CacheDto
import dto.CachePoint
import helper.ApiParams
import helper.Endpoints
import helper.asData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

internal interface NetworkCache {
    suspend fun lastUpdate(point: CachePoint): CacheDto
}

internal class NetworkCacheImpl(
    private val httpClient: HttpClient
) : NetworkCache {
    override suspend fun lastUpdate(point: CachePoint): CacheDto =
        httpClient.get {
            url {
                parameters.append(ApiParams.POINT, point.name)
                path(Endpoints.Cache.lastUpdate)
            }
        }.body<BaseResponse<CacheDto>>().asData()
}