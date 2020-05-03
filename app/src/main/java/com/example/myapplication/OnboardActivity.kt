package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OnboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Info.load(this)
        if(Info.onboardComplete) {
            val main = Intent(this, MainActivity::class.java)
            startActivity(main)
            finish()
        } else {
            setContentView(R.layout.activity_onboard)
        }
    }
}
