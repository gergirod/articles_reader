package ger.girod.notesreader.data.providers

import android.content.Context
import android.content.SharedPreferences
import org.koin.dsl.module

fun providePreferencesManager(context: Context) : SharedPreferences {
    return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
}

val sharePrefenceModule = module {
    single { providePreferencesManager(get()) }
    single { PreferencesManager(get()) }
}