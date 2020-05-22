package ger.girod.notesreader.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ger.girod.notesreader.R
import ger.girod.notesreader.presentation.main.MainActivity
import android.content.Intent
import android.os.Handler


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)

    }
}