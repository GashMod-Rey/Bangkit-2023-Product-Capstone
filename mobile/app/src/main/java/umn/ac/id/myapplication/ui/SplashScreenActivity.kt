package umn.ac.id.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import umn.ac.id.myapplication.R
import umn.ac.id.myapplication.ui.applicantpage.LoginOptionActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val splashIntent = Intent(this, LoginOptionActivity::class.java)
            startActivity(splashIntent)
            finish()
        },2000)
    }
}