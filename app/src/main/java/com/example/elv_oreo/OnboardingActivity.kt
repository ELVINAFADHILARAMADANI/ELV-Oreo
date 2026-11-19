package com.example.elv_oreo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

data class OnboardingItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

class OnboardingAdapter(private val items: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgOnboarding)
        val title: TextView = view.findViewById(R.id.tvTitleOnboarding)
        val desc: TextView = view.findViewById(R.id.tvDescOnboarding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = items[position]
        holder.img.setImageResource(item.imageRes)
        holder.title.text = item.title
        holder.desc.text = item.description
    }

    override fun getItemCount(): Int = items.size
}

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: Button
    private lateinit var tvSkip: TextView
    private lateinit var tvIndicator: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPagerOnboarding)
        btnNext = findViewById(R.id.btnNextOnboarding)
        tvSkip = findViewById(R.id.tvSkip)
        tvIndicator = findViewById(R.id.tvIndicator)

        // Ganti R.mipmap.ic_launcher dengan ikon/logo sawit milikmu di res/drawable jika sudah ada
        val slides = listOf(
            OnboardingItem(
                R.mipmap.ic_launcher,
                "Catat Aktivitas Kebun",
                "Catat pemupukan, penyiraman, dan hasil panen sawit langsung dari lapangan secara cepat dan rapi."
            ),
            OnboardingItem(
                R.mipmap.ic_launcher,
                "Pantau Riwayat & Blok",
                "Semua catatan aktivitas per blok kebun tersimpan terstruktur sehingga meminimalkan risiko data hilang."
            ),
            OnboardingItem(
                R.mipmap.ic_launcher,
                "Evaluasi Hasil Panen",
                "Rekapitulasi hasil timbangan panen menjadi lebih mudah dan akurat untuk memantau performa kebun."
            )
        )

        viewPager.adapter = OnboardingAdapter(slides)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tvIndicator.text = "${position + 1} / ${slides.size}"
                btnNext.text = if (position == slides.size - 1) "Mulai Sekarang" else "Lanjut"
            }
        })

        btnNext.setOnClickListener {
            if (viewPager.currentItem + 1 < slides.size) {
                viewPager.currentItem += 1
            } else {
                pindahKeLogin()
            }
        }

        tvSkip.setOnClickListener {
            pindahKeLogin()
        }
    }

    private fun pindahKeLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}