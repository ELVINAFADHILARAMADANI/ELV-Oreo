package com.example.elv_oreo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDetail = findViewById<Button>(R.id.btnDetail)
        val btnWebSawit = findViewById<Button>(R.id.btnWebSawit)

        // Buka Halaman Catat Panen (CatatPanenActivity)
        btnDetail.setOnClickListener {
            val intent = Intent(this, CatatPanenActivity::class.java)
            startActivity(intent)
        }

        // Buka Halaman Web (WebActivity)
        btnWebSawit.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }
    }
}