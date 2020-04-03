package ger.girod.notesreader.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.daos.CategoryWithArticles
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category


@Database(entities = [Article::class, Category::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun initializeDatabaseInstance(context: Context) {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, AppDataBase::class.java, "article_database"
                    ).build()
                }
            }
        }

        fun getDatabaseInstance(): AppDataBase? {
            checkNotNull(INSTANCE) { AppDataBase::class.java.simpleName + " is not initialized, call initializeInstance(..) method first." }
            return INSTANCE
        }

    }
}