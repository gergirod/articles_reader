package ger.girod.notesreader

import android.app.Application
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.domain.entities.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ForLaterApplication : Application(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate() {
        super.onCreate()
        AppDataBase.initializeDatabaseInstance(applicationContext)
        saveInitialCategory()
        PreferencesManager.initialiaze(applicationContext)

    }

    private fun saveInitialCategory() {
        launch {
            val count = AppDataBase.getDatabaseInstance()!!.articleDao().getCategoriesCount()
            if (count == 0) {
                AppDataBase.getDatabaseInstance()!!.articleDao().apply {
                    saveCategory(Category(0, "Default Category", ""))
                }
            }
        }
    }
}