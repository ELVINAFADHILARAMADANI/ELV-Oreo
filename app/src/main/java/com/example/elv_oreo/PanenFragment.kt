package com.example.elv_oreo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class PanenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_panen, container, false)

        val etBlok = view.findViewById<EditText>(R.id.etBlokKebun)
        val etBerat = view.findViewById<EditText>(R.id.etBeratPanen)
        val etHargaTBS = view.findViewById<EditText>(R.id.etHargaTBS)
        val btnHitungEstimasi = view.findViewById<Button>(R.id.btnHitungEstimasi)
        val tvHasilEstimasi = view.findViewById<TextView>(R.id.tvHasilEstimasi)

        val etTanggal = view.findViewById<EditText>(R.id.etTanggalPanen)
        val etCatatan = view.findViewById<EditText>(R.id.etCatatan)
        val btnSimpan = view.findViewById<Button>(R.id.btnSimpan)

        // Logika Hitung Estimasi Pendapatan
        btnHitungEstimasi?.setOnClickListener {
            val beratStr = etBerat?.text.toString().trim()
            val hargaStr = etHargaTBS?.text.toString().trim()

            if (beratStr.isEmpty() || hargaStr.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi jumlah berat panen dan harga TBS harian!", Toast.LENGTH_SHORT).show()
            } else {
                val berat = beratStr.toDoubleOrNull() ?: 0.0
                val harga = hargaStr.toDoubleOrNull() ?: 0.0
                val totalPendapatan = berat * harga

                tvHasilEstimasi?.text = String.format("Rp %,.2f", totalPendapatan)
            }
        }

        // Logika Simpan Panen
        btnSimpan?.setOnClickListener {
            val blok = etBlok?.text.toString().trim()
            val berat = etBerat?.text.toString().trim()
            val tanggal = etTanggal?.text.toString().trim()
            val catatan = etCatatan?.text.toString().trim()

            if (blok.isEmpty() || berat.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi Blok Kebun dan Jumlah Panen!", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPref = requireActivity().getSharedPreferences("data_panen_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("last_blok_kebun", blok)
                editor.putString("last_berat_panen", berat)
                editor.putString("last_tanggal_panen", tanggal)
                editor.putString("last_catatan_panen", catatan)
                editor.apply()

                Toast.makeText(requireContext(), "Data panen berhasil disimpan!", Toast.LENGTH_SHORT).show()

                // Bersihkan form
                etBlok?.text?.clear()
                etBerat?.text?.clear()
                etHargaTBS?.text?.clear()
                etTanggal?.text?.clear()
                etCatatan?.text?.clear()
                tvHasilEstimasi?.text = "Rp 0"
            }
        }

        return view
    }
}