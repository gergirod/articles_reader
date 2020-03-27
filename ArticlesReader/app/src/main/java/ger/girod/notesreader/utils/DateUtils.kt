package ger.girod.notesreader.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun toSimpleString(date: Date): String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(date)
    }
}