package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState != null) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)

        } else {
            super.onCreate(savedInstanceState)

//        Info.reset(this)
            Info.load(this)
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)

            if(Info.birthDate != Info.birthDateNotSet) {
//            findNavController().navigate(HomeFragment)
//            swapScreen(3)

//                findNavController(nav_host_fragment.id).navigate(R.id.HomeFragment)
//                findNavController(nav_host_fragment.id).popBackStack()
//                findNavController(nav_host_fragment.id).setGraph(NavGraph(R.id.nav_graph_home))

                // findNavController(nav_host_fragment.id).navigate(R.id.HomeFragment, null, navOptions {  })

                val nc = findNavController(nav_host_fragment.id)
                nc.popBackStack(R.id.FirstFragment, true)
                nc.navigate(R.id.HomeFragment)
            }
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
            4 -> ft.replace(R.id.nav_host_fragment, SettingsFragment())
        }

        ft.commit()
    }
}
