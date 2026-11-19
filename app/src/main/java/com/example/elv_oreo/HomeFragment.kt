package com.example.elv_oreo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var tvTargetStatus: TextView
    private lateinit var pbTargetPanen: ProgressBar
    private lateinit var tvPersentaseTarget: TextView
    private lateinit var btnUbahTarget: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnDetail = view.findViewById<Button>(R.id.btnDetail)
        val btnWebSawit = view.findViewById<Button>(R.id.btnWebSawit)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        tvTargetStatus = view.findViewById(R.id.tvTargetStatus)
        pbTargetPanen = view.findViewById(R.id.pbTargetPanen)
        tvPersentaseTarget = view.findViewById(R.id.tvPersentaseTarget)
        btnUbahTarget = view.findViewById(R.id.btnUbahTarget)

        // Event Klik Tombol Ubah Target oleh Petani
        btnUbahTarget.setOnClickListener {
            showDialogUbahTarget()
        }

        btnDetail?.setOnClickListener {
            val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNav?.selectedItemId = R.id.nav_panen
        }

        btnWebSawit?.setOnClickListener {
            val intent = Intent(requireContext(), WebActivity::class.java)
            startActivity(intent)
        }

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

    override fun onResume() {
        super.onResume()
        updateTargetProgress()
    }

    // Pop-up Dialog untuk Petani Mengubah Target Panen
    private fun showDialogUbahTarget() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Set Target Panen Bulanan (kg)")

        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.hint = "Masukkan target (misal: 3000)"
        builder.setView(input)

        builder.setPositiveButton("Simpan") { dialog, _ ->
            val targetBaruStr = input.text.toString().trim()
            if (targetBaruStr.isNotEmpty()) {
                val targetBaru = targetBaruStr.toFloatOrNull() ?: 5000f

                // Simpan target baru ke SharedPreferences
                val sharedPref = requireActivity().getSharedPreferences("data_panen_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putFloat("target_panen_monthly", targetBaru)
                editor.apply()

                Toast.makeText(requireContext(), "Target panen berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                updateTargetProgress()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    // Logika Kalkulasi Target Panen Dinamis
    private fun updateTargetProgress() {
        val sharedPref = requireActivity().getSharedPreferences("data_panen_pref", Context.MODE_PRIVATE)
        val lastBeratStr = sharedPref.getString("last_berat_panen", "0")

        // Ambil target dari SharedPreferences, default 5000 jika belum pernah diubah
        val targetPanen = sharedPref.getFloat("target_panen_monthly", 5000f).toDouble()

        val lastBerat = lastBeratStr?.toDoubleOrNull() ?: 0.0

        val persentase = if (targetPanen > 0) {
            ((lastBerat / targetPanen) * 100).toInt().coerceAtMost(100)
        } else {
            0
        }

        tvTargetStatus.text = String.format("Terkumpul: %,.0f kg / Target: %,.0f kg", lastBerat, targetPanen)
        pbTargetPanen.progress = persentase
        tvPersentaseTarget.text = "Pencapaian: $persentase%"
    }
}