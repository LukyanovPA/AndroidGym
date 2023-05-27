package database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "category")
@Serializable
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val questionsCount: Int?
)
