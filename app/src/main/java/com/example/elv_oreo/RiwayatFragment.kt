package com.example.elv_oreo

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RiwayatFragment : Fragment() {

    private val semuaData = mutableListOf<ItemRiwayat>()
    private lateinit var adapter: RiwayatAdapter
    private var filterAktif = "Semua"
    private var kataKunci = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_riwayat, container, false)

        val etCari = view.findViewById<EditText>(R.id.etCariRiwayat)
        val btnSemua = view.findViewById<Button>(R.id.btnFilterSemua)
        val btnPupuk = view.findViewById<Button>(R.id.btnFilterPupuk)
        val btnSiram = view.findViewById<Button>(R.id.btnFilterSiram)
        val btnPanen = view.findViewById<Button>(R.id.btnFilterPanen)
        val rvRiwayat = view.findViewById<RecyclerView>(R.id.rvRiwayat)

        muatDataRiwayat()

        adapter = RiwayatAdapter(semuaData)
        rvRiwayat.layoutManager = LinearLayoutManager(requireContext())
        rvRiwayat.adapter = adapter

        val tombolFilter = listOf(btnSemua, btnPupuk, btnSiram, btnPanen)
        fun setTombolAktif(aktif: Button, kategori: String) {
            filterAktif = kategori
            tombolFilter.forEach { btn ->
                if (btn == aktif) {
                    btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#12203A"))
                    btn.setTextColor(Color.WHITE)
                } else {
                    btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#E2E8F0"))
                    btn.setTextColor(Color.parseColor("#334155"))
                }
            }
            terapkanFilter()
        }

        btnSemua.setOnClickListener { setTombolAktif(btnSemua, "Semua") }
        btnPupuk.setOnClickListener { setTombolAktif(btnPupuk, "Pemupukan") }
        btnSiram.setOnClickListener { setTombolAktif(btnSiram, "Penyiraman") }
        btnPanen.setOnClickListener { setTombolAktif(btnPanen, "Panen") }

        etCari.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                kataKunci = s.toString().trim()
                terapkanFilter()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    private fun muatDataRiwayat() {
        semuaData.clear()

        // Ambil data panen terbaru yang baru saja disimpan di PanenFragment
        val sharedPref = requireActivity().getSharedPreferences("data_panen_pref", Context.MODE_PRIVATE)
        val lastBlok = sharedPref.getString("last_blok_kebun", null)
        val lastBerat = sharedPref.getString("last_berat_panen", null)
        val lastTanggal = sharedPref.getString("last_tanggal_panen", "")

        if (!lastBlok.isNullOrEmpty() && !lastBerat.isNullOrEmpty()) {
            semuaData.add(
                ItemRiwayat(
                    jenis = "Panen",
                    detail = "$lastBlok - $lastBerat kg",
                    waktu = if (lastTanggal.isNullOrEmpty()) "Baru saja" else lastTanggal,
                    warnaHex = "#B84A1A"
                )
            )
        }

        // Data riwayat kebun sesuai wireframe Layar 5
        semuaData.addAll(
            listOf(
                ItemRiwayat("Panen", "Blok A1 - 120 kg", "08:30", "#B84A1A"),
                ItemRiwayat("Pemupukan", "Blok B1 - 160 kg (NPK)", "07:15", "#F5A524"),
                ItemRiwayat("Penyiraman", "Blok C1 - Kondisi normal", "Kemarin", "#12203A"),
                ItemRiwayat("Panen", "Blok A2 - 95 kg", "15 Nov", "#B84A1A"),
                ItemRiwayat("Pemupukan", "Blok A1 - 140 kg (Urea)", "12 Nov", "#F5A524"),
                ItemRiwayat("Penyiraman", "Blok B1 - Penyiraman pagi", "10 Nov", "#12203A"),
                ItemRiwayat("Panen", "Blok C1 - 210 kg", "07 Nov", "#B84A1A"),
                ItemRiwayat("Pemupukan", "Blok A2 - 150 kg (KCL)", "03 Nov", "#F5A524")
            )
        )
    }

    private fun terapkanFilter() {
        val hasil = semuaData.filter { item ->
            val cocokKategori = (filterAktif == "Semua") || item.jenis.equals(filterAktif, ignoreCase = true)
            val cocokTeks = kataKunci.isEmpty() ||
                    item.jenis.contains(kataKunci, ignoreCase = true) ||
                    item.detail.contains(kataKunci, ignoreCase = true)
            cocokKategori && cocokTeks
        }
        adapter.updateData(hasil)
    }
}