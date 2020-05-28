package ger.girod.notesreader.data.providers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ger.girod.notesreader.data.database.AppDataBase

const val FILE_NAME = "articles_reader"
const val LAST_CATEGORY_SELECTED = "last_category_selected"

class PreferencesManager(private val preferences: SharedPreferences) {

    fun saveLastCategorySelectedId(id : Long) {
        preferences!!.edit().putLong(LAST_CATEGORY_SELECTED, id).apply()
    }

    fun getLastCategorySelectedId() : Long {
        return preferences!!.getLong(LAST_CATEGORY_SELECTED, 1)
    }
}