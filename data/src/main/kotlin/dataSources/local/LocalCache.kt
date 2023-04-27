package dataSources.local

import database.dao.LastUpdateDao
import database.entity.CacheEntity
import dto.CachePoint

internal interface LocalCache {
    suspend fun lastUpdate(point: CachePoint): CacheEntity?

    suspend fun insert(cacheEntity: CacheEntity): Long

    suspend fun update(cacheEntity: CacheEntity)
}

internal class LocalCacheImpl(
    private val dao: LastUpdateDao
) : LocalCache {
    override suspend fun lastUpdate(point: CachePoint): CacheEntity? =
        dao.lastUpdate(point = point.name)

    override suspend fun insert(cacheEntity: CacheEntity): Long =
        dao.insert(cacheEntity = cacheEntity)

    override suspend fun update(cacheEntity: CacheEntity) =
        dao.update(cacheEntity = cacheEntity)
}