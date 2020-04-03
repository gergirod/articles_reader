package ger.girod.notesreader.data.providers

import android.content.Context
import android.content.SharedPreferences
import ger.girod.notesreader.data.database.AppDataBase

const val FILE_NAME = "articles_reader"
const val LAST_CATEGORY_SELECTED = "last_category_selected"

class PreferencesManager(context: Context) {

    private var  preferences : SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun saveLastCategorySelectedId(id : Long) {
        preferences!!.edit().putLong(LAST_CATEGORY_SELECTED, id).apply()
    }

    fun getLastCategorySelectedId() : Long {
        return preferences!!.getLong(LAST_CATEGORY_SELECTED, 1)
    }

    companion object  {

        @Volatile
        private var INSTANCE: PreferencesManager? = null

        fun initialiaze(context: Context) {
            if (INSTANCE == null) {
                synchronized(PreferencesManager::class.java) {
                    INSTANCE = PreferencesManager(context)
                }
            }
        }

        fun getInstance() : PreferencesManager? {
            checkNotNull(INSTANCE) {PreferencesManager::class.java.simpleName + " is not initialized, call initializeInstance(..) method first." }
            return INSTANCE
        }
    }
}