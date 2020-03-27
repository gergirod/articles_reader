package ger.girod.notesreader.domain.entities.utils

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun localDateToTimestamp(date: Date): Long {
        return date.time
    }
}