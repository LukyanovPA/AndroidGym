package database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.dao.AnswersDao
import database.dao.CategoryDao
import database.dao.LastUpdateDao
import database.dao.QuestionsDao
import database.dao.SubcategoryDao
import database.entity.AnswerEntity
import database.entity.CacheEntity
import database.entity.CategoryEntity
import database.entity.QuestionEntity
import database.entity.SubcategoryEntity

@Database(
    entities = [
        CategoryEntity::class,
        CacheEntity::class,
        SubcategoryEntity::class,
        QuestionEntity::class,
        AnswerEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun category(): CategoryDao
    abstract fun lastUpdate(): LastUpdateDao
    abstract fun subcategories(): SubcategoryDao
    abstract fun questions(): QuestionsDao
    abstract fun answers(): AnswersDao
}