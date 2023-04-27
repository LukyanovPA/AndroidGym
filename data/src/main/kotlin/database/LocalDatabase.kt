package database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.dao.CategoryDao
import database.entity.CategoryEntity

@Database(
    entities = [
        CategoryEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun category(): CategoryDao
}