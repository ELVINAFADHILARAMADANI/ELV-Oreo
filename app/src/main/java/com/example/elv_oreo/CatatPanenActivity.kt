package com.example.elv_oreo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class CatatPanenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catat_panen)

        val toolbar = findViewById<Toolbar>(R.id.toolbarCatatPanen)
        val etBlok = findViewById<EditText>(R.id.etBlokKebun)
        val etBerat = findViewById<EditText>(R.id.etBeratPanen)

        // --- PENAMBAHAN FITUR 1: Inisialisasi View Kalkulator ---
        val etHargaTBS = findViewById<EditText>(R.id.etHargaTBS)
        val btnHitungEstimasi = findViewById<Button>(R.id.btnHitungEstimasi)
        val tvHasilEstimasi = findViewById<TextView>(R.id.tvHasilEstimasi)

        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        // Setup Toolbar Back Button
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // --- PENAMBAHAN FITUR 1: Logika Hitung Estimasi Pendapatan ---
        btnHitungEstimasi?.setOnClickListener {
            val beratStr = etBerat?.text.toString().trim()
            val hargaStr = etHargaTBS?.text.toString().trim()

            if (beratStr.isEmpty() || hargaStr.isEmpty()) {
                Toast.makeText(this, "Harap isi jumlah berat panen dan harga TBS harian!", Toast.LENGTH_SHORT).show()
            } else {
                val berat = beratStr.toDoubleOrNull() ?: 0.0
                val harga = hargaStr.toDoubleOrNull() ?: 0.0
                val totalPendapatan = berat * harga

                tvHasilEstimasi?.text = String.format("Estimasi Pendapatan: Rp %,.2f", totalPendapatan)
            }
        }

        btnSimpan?.setOnClickListener {
            val blok = etBlok?.text.toString().trim()
            val berat = etBerat?.text.toString().trim()

            if (berat.isEmpty() || blok.isEmpty()) {
                Toast.makeText(this, "Harap isi Blok Kebun dan Jumlah Panen!", Toast.LENGTH_SHORT).show()
            } else {
                // Simpan data panen ke SharedPreferences
                val sharedPref = getSharedPreferences("data_panen_pref", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("last_blok_kebun", blok)
                editor.putString("last_berat_panen", berat)
                editor.apply()

                Toast.makeText(this, "Data panen berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke layar sebelumnya
            }
        }
    }
}