package com.example.elv_oreo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDetail = findViewById<Button>(R.id.btnDetail)
        val btnWebSawit = findViewById<Button>(R.id.btnWebSawit)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Buka Halaman Catat Panen
        btnDetail.setOnClickListener {
            val intent = Intent(this, CatatPanenActivity::class.java)
            startActivity(intent)
        }

        // Buka Halaman Web
        btnWebSawit.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }

        // 3. Logout dan Hapus SharedPreferences
        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.clear() // Menghapus data sesi login
                    editor.apply()

                    dialog.dismiss()

                    // Kembali ke LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }
}