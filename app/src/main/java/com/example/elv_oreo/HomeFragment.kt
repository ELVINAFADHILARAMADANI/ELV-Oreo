package com.example.elv_oreo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnDetail = view.findViewById<Button>(R.id.btnDetail)
        val btnWebSawit = view.findViewById<Button>(R.id.btnWebSawit)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Pindah otomatis ke Tab Panen di BottomNavigationView
        btnDetail?.setOnClickListener {
            val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNav?.selectedItemId = R.id.nav_panen
        }

        // Buka Halaman Web Sawit
        btnWebSawit?.setOnClickListener {
            val intent = Intent(requireContext(), WebActivity::class.java)
            startActivity(intent)
        }

        // Logout dan Hapus Session Login
        btnLogout?.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()

                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }

        return view
    }
}