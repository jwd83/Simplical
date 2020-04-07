package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(Info.spFilename, Context.MODE_PRIVATE)

        Info.height = prefs.getDouble(Info.spKeyHeight, 0.0)
        Info.weight = prefs.getDouble(Info.spKeyWeight, 0.0)
        Info.activityLevel = prefs.getDouble(Info.spKeyActivityLevel, 0.0)
        Info.birthDate = prefs.getString(Info.spKeyBirthDate, "")
        Info.male = prefs.getBoolean(Info.spKeyMale, false)
        Info.rate = prefs.getDouble(Info.spKeyRate, 0.0)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
