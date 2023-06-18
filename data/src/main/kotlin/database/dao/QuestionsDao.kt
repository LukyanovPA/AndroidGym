package database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import database.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questions: List<QuestionEntity>)

    @Query("SELECT * FROM questions")
    fun getAll(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE subcategoryId = :subcategoryId")
    fun getQuestionsBySubcategoryId(subcategoryId: Int): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getById(id: Int): QuestionEntity

    @Update
    suspend fun update(question: QuestionEntity)
}