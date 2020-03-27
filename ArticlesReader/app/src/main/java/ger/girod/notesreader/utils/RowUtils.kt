package ger.girod.notesreader.utils

import android.content.Context
import androidx.core.content.ContextCompat
import ger.girod.notesreader.R

object RowUtils {

    fun getRowBackgrounColor(isRead: Boolean, context: Context): Int {

        return if (isRead) context.resources.getColor(R.color.white) else context.resources.getColor(
            R.color.row_background
        )
    }

    fun getTextAndIconColors(isRead: Boolean, context: Context): Int {
        return if (isRead) ContextCompat.getColor(
            context,
            R.color.row_background
        ) else ContextCompat.getColor(context, R.color.white)
    }

    fun getCheckIconColor(isRead: Boolean, context: Context): Int {
        return if (isRead) ContextCompat.getColor(
            context,
            R.color.disable
        ) else ContextCompat.getColor(context, R.color.white)
    }

}