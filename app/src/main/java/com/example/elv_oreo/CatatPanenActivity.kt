package com.example.elv_oreo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        // Setup Toolbar Back Button
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
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