package ger.girod.notesreader.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ger.girod.notesreader.R

class MainActivity : AppCompatActivity() {

    private var intentString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        when {
            intent.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    intentString = intent.getStringExtra(Intent.EXTRA_TEXT)
                }
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(intentString))
                .commitNow()
        }


    }
}
