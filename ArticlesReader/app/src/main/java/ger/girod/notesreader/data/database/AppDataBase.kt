package ger.girod.notesreader.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category


@Database(entities = [Article::class, Category::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}