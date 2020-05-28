package ger.girod.notesreader

import android.app.Application
import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.database.databaseModule
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.data.providers.sharePrefenceModule
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.useCaseModules
import ger.girod.notesreader.presentation.utils.viewModelModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext

class ForLaterApplication : Application(), CoroutineScope, KoinComponent {


    val articleDao : ArticleDao by inject()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ForLaterApplication)
            modules(listOf(
                databaseModule,
                useCaseModules,
                viewModelModules,
                sharePrefenceModule
            ))
        }
        saveInitialCategory()

    }

    private fun saveInitialCategory() {
        launch {
            val count = articleDao.getCategoriesCount()
            if (count == 0) {
               articleDao.apply {
                    saveCategory(Category(0, "Default Category", ""))
                }
            }
        }
    }
}