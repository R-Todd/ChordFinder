package com.example.rickopedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.overflowIcon
            ?.setTint(ContextCompat.getColor(this, R.color.rick_blue))

        // Now NavUI can talk to it
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        // allow the Up button to work
        return findNavController(R.id.nav_host_fragment)
            .navigateUp() || super.onSupportNavigateUp()
    }
}
