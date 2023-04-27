package database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_update")
data class CacheEntity(
    @PrimaryKey
    val point: String,
    val update: Long
)
