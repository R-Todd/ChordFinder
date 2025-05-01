package com.example.rickopedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1) Wire up Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 2) Find the NavController
        navController = findNavController(R.id.nav_host_fragment)

        // 3) Tell it which screens are "top level" (no back arrow)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.characterListFragment,
                R.id.favoriteCharactersFragment
            )
        )

        // 4) Hook the ActionBar to NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Delegate the back arrow to NavController
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
