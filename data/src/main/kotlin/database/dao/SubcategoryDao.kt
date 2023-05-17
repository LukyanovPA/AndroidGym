package database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import database.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubcategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subcategories: List<SubcategoryEntity>)

    @Query("SELECT * FROM subcategory")
    fun getAll(): Flow<List<SubcategoryEntity>>
}