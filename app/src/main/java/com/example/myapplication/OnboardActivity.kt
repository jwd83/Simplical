package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OnboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Info.load(this)
        if(Info.birthDateNotSet == Info.birthDate) {
            setContentView(R.layout.activity_onboard)
        } else {
            val main = Intent(this, MainActivity::class.java)
            startActivity(main)
            finish()
        }
    }
}
