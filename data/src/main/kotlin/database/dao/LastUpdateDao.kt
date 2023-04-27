package database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import database.entity.CacheEntity

@Dao
interface LastUpdateDao {
    @Query("SELECT * FROM last_update WHERE point = :point")
    suspend fun lastUpdate(point: String): CacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cacheEntity: CacheEntity): Long

    @Update
    suspend fun update(cacheEntity: CacheEntity)
}