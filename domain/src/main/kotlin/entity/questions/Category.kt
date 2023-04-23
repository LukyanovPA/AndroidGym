package entity.questions

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "category")
@Serializable
data class Category(
    @PrimaryKey
    val id: Int,
    val name: String
)
