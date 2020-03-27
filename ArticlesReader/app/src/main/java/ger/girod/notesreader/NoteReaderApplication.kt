package ger.girod.notesreader

import android.app.Application
import ger.girod.notesreader.data.database.AppDataBase

class NoteReaderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDataBase.initializeDatabaseInstance(applicationContext)
    }
}