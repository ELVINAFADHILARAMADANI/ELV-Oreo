package com.example.elv_oreo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Set Fragment default (HomeFragment) saat aplikasi pertama kali dimuat
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // Listener untuk navigasi via BottomNavigationView
        bottomNav.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_panen -> PanenFragment() // Atau Fragment fitur panen kamu
                R.id.nav_profile -> ProfileFragment() // Halaman Profil Developer
                else -> HomeFragment()
            }
            loadFragment(selectedFragment)
        }
    }

    // Function pendukung untuk mengganti Fragment di fragmentContainer
    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        return true
    }
}