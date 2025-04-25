// File: ui/MainActivity.kt
// Functionality: Loads the main layout, injects ChordSearchFragment into the container,
// and adjusts the container's padding so content isn't covered by system bars.

package com.example.chordfinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chordfinder.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for the activity.
        setContentView(R.layout.activity_main)

        // Load the ChordSearchFragment into the container if it's not already loaded.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, ChordSearchFragment())
                .commit()
        }

        // Adjust the main view's padding based on the system bars (status bar and navigation bar).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}