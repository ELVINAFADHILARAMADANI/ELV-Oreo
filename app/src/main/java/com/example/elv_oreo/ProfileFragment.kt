package com.example.elv_oreo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Hubungkan ke ID tombol di XML
        val btnBack = view.findViewById<Button>(R.id.btnBack)

        // Logika berpindah ke Tab Home saat tombol diklik
        btnBack?.setOnClickListener {
            val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNav?.selectedItemId = R.id.nav_home
        }

        return view
    }
}