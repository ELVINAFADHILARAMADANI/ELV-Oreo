package com.example.elv_oreo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ItemRiwayat(
    val jenis: String,      // "Panen", "Pemupukan", "Penyiraman"
    val detail: String,     // "Blok A1 - 120 kg"
    val waktu: String,      // "08:30" atau "19/11/2026"
    val warnaHex: String
)

class RiwayatAdapter(
    private var listTampil: List<ItemRiwayat>
) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewBadgeWarna: View = view.findViewById(R.id.viewBadgeWarna)
        val tvItemJenis: TextView = view.findViewById(R.id.tvItemJenis)
        val tvItemDetail: TextView = view.findViewById(R.id.tvItemDetail)
        val tvItemTanggal: TextView = view.findViewById(R.id.tvItemTanggal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat_panen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listTampil[position]
        holder.tvItemJenis.text = item.jenis
        holder.tvItemDetail.text = item.detail
        holder.tvItemTanggal.text = item.waktu
        holder.viewBadgeWarna.setBackgroundColor(Color.parseColor(item.warnaHex))
    }

    override fun getItemCount(): Int = listTampil.size

    fun updateData(dataBaru: List<ItemRiwayat>) {
        listTampil = dataBaru
        notifyDataSetChanged()
    }
}