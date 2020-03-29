package ger.girod.notesreader.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "id") val id: Long,
                    @ColumnInfo(name = "category_name") val name : String,
                    @ColumnInfo(name = "category_color") val color : String)