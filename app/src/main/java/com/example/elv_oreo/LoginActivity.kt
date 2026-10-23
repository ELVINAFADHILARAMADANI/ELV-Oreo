package com.example.elv_oreo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegisterGmail = findViewById<Button>(R.id.btnRegisterGmail)

        // Event Listener Tombol Login
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Ambil data dari SharedPreferences
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val savedUsername = sharedPref.getString("USERNAME", "") ?: ""
            val savedEmail = sharedPref.getString("EMAIL", "") ?: ""
            val savedPassword = sharedPref.getString("PASSWORD", "") ?: ""

            // Logika 1: Username sama dengan Password
            val isDefaultValid = username.isNotEmpty() && username == password

            // Logika 2: Cocok dengan Username/Email & Password dari SharedPreferences
            val isPrefUserValid = (username.equals(savedUsername, ignoreCase = true) || username.equals(savedEmail, ignoreCase = true)) &&
                    password == savedPassword && password.isNotEmpty()

            if (isDefaultValid || isPrefUserValid) {
                // Simpan status session login
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.apply()

                // Pindah ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Event Listener Tombol Register With Gmail (Soal 1)
        btnRegisterGmail.setOnClickListener {
            showEmailInputDialog()
        }
    }

    // --- FUNGSI DIALOG REGISTER GMAIL (SOAL 1) ---

    private fun showEmailInputDialog() {
        val builder = MaterialAlertDialogBuilder(this)
        val input = EditText(this).apply {
            hint = "Masukkan Email Gmail"
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        val container = FrameLayout(this).apply {
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = 50
                rightMargin = 50
            }
            addView(input, params)
        }

        builder.setTitle("Register With Gmail")
            .setView(container)
            .setPositiveButton("Lanjut") { _, _ ->
                val email = input.text.toString().trim()
                validateAndNavigateToRegister(email)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun validateAndNavigateToRegister(email: String) {
        if (email.isEmpty()) {
            showErrorDialog("Email tidak boleh kosong!")
            return
        }

        if (!email.endsWith("@gmail.com", ignoreCase = true)) {
            showErrorDialog("Email harus menggunakan domain @gmail.com!")
            return
        }

        val intent = Intent(this, RegisterActivity::class.java).apply {
            putExtra("EXTRA_EMAIL", email)
        }
        startActivity(intent)
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error Validasi")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}