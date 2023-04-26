package database.entity

import Constants.INT_ZERO
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "category")
@Serializable
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val questionsCount: Int = INT_ZERO
)
