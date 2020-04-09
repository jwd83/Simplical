package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Info.reset(this)
        Info.load(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if(Info.birthDate != Info.birthDateNotSet) {
            swapScreen(3)
        }
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

    fun swapScreen(screen: Int) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

        when (screen) {
            3 -> ft.replace(R.id.nav_host_fragment, HomeFragment())
        }

        ft.commit()
    }
}
