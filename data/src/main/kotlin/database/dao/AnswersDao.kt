package database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import database.entity.AnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answers: List<AnswerEntity>)

    @Query("DELETE FROM answers")
    suspend fun deleteAll()

    @Query("SELECT * FROM answers WHERE questionId = :questionId")
    fun getAllByQuestionId(questionId: Int): Flow<List<AnswerEntity>>

    @Query("SELECT * FROM answers WHERE id = :id")
    suspend fun getAnswerById(id: Int): AnswerEntity

    @Query("SELECT * FROM answers")
    fun getAll(): Flow<List<AnswerEntity>>

    @Update
    suspend fun update(answer: AnswerEntity)
}