package ger.girod.notesreader.data.database

import androidx.room.Room.databaseBuilder
import org.koin.dsl.module

val databaseModule = module {
    single { databaseBuilder(get(), AppDataBase::class.java, "article_database").build() }
    single { get<AppDataBase>().articleDao() }

}