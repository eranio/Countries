package com.erantal.assignment.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.erantal.assignment.R

class SplashActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        handler = Handler()
        handler.postDelayed({
            goToMainActivity()
        },2000)
    }

    private fun goToMainActivity() {
        var intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}