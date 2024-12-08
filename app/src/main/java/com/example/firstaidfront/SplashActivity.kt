package com.example.firstaidfront

import android.annotation.SuppressLint


import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper



@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Simulating a loading process with a delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to KeycloakLoginActivity
            startActivity(Intent(this, KeycloakLoginActivity::class.java))
            finish()
        }, 3000) // 3 seconds delay
    }
}