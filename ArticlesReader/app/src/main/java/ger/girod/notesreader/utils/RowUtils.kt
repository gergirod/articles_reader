package ger.girod.notesreader.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import ger.girod.notesreader.R

object RowUtils {

    fun getBackgrounColor(isRead: Boolean, context: Context): Int {

        return if (isRead) context.resources.getColor(R.color.white) else context.resources.getColor(
            R.color.white
        )
    }

    fun getSecondaryBackgroun(isRead: Boolean, context: Context): ColorStateList? {
        return if (isRead) ContextCompat.getColorStateList(context, R.color.white)
        else ContextCompat.getColorStateList(context, R.color.blue_dark)
    }

    fun getTextAndIconColors(isRead: Boolean, context: Context): Int {
        return if (isRead) ContextCompat.getColor(
            context,
            R.color.disable
        ) else ContextCompat.getColor(context, R.color.black_light)
    }

    fun getCheckIconColor(isRead: Boolean, context: Context): Int {
        return if (isRead) ContextCompat.getColor(
            context,
            R.color.disable
        ) else ContextCompat.getColor(context, R.color.white)
    }

}