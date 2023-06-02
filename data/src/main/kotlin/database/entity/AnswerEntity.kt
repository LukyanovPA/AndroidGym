package database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "answers")
data class AnswerEntity(
    @PrimaryKey
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val subcategoryId: Int,
    val subcategoryName: String,
    val questionId: Int,
    val question: String,
    val answer: String,
    val isFavourites: Boolean = false
)
