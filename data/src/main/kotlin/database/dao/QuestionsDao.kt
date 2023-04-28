package database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import database.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questions: List<QuestionEntity>)

    @Query("SELECT * FROM questions WHERE subcategoryId = :subcategoryId")
    fun getAllBySubcategoryId(subcategoryId: Int): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions")
    fun getAll(): Flow<List<QuestionEntity>>
}