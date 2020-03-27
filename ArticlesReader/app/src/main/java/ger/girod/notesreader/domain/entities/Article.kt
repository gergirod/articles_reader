package ger.girod.notesreader.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ger.girod.notesreader.domain.entities.utils.DateConverter
import java.util.*

@TypeConverters(DateConverter::class)
@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "is_read") var isRead: Boolean,
    @ColumnInfo(name = "date_saved") var dateSaved: Date
)