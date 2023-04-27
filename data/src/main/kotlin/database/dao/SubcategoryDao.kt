package database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import database.entity.SubcategoryEntity

@Dao
interface SubcategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subcategories: List<SubcategoryEntity>)

    @Query("SELECT * FROM subcategory")
    fun getAll(): PagingSource<Int, SubcategoryEntity>
}