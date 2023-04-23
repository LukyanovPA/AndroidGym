package database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.dao.CategoryDao
import entity.questions.Category

@Database(
    entities = [
        Category::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun category(): CategoryDao
}