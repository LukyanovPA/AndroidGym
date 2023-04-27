package database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "subcategory")
data class SubcategoryEntity(
    @PrimaryKey
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val name: String
)
