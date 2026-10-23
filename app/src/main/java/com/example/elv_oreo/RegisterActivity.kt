package com.example.elv_oreo

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNama = findViewById<EditText>(R.id.etNama)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // 1. Tangkap email dari Intent LoginActivity
        val emailFromGmail = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        etEmail.setText(emailFromGmail)

        // 2. Kunci kolom email agar tidak bisa diubah (Sesuai Soal 2)
        etEmail.isEnabled = false

        // 3. Tombol Registrasi
        btnRegister.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validasi Field Wajib Isi
            if (nama.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showDialog("Error Validasi", "Semua kolom inputan wajib diisi!")
                return@setOnClickListener
            }

            // Validasi Username Tanpa Spasi
            if (username.contains(" ")) {
                showDialog("Error Validasi", "Username tidak boleh mengandung spasi!")
                return@setOnClickListener
            }

            // Validasi Minimal Password 6 Karakter
            if (password.length < 6) {
                showDialog("Error Validasi", "Password minimal harus 6 karakter!")
                return@setOnClickListener
            }

            // Simpan Data ke SharedPreferences
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("NAMA", nama)
            editor.putString("EMAIL", email)
            editor.putString("USERNAME", username)
            editor.putString("PASSWORD", password)
            editor.apply()

            // Tampilkan Dialog Berhasil dan Kembali ke Login
            MaterialAlertDialogBuilder(this)
                .setTitle("Informasi")
                .setMessage("Registrasi Berhasil")
                .setPositiveButton("OK") { _, _ ->
                    finish() // Kembali ke LoginActivity
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun showDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}