package com.example.klubbuku_kleponresingteam.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Adapter.BukuAdapter
import com.example.klubbuku_kleponresingteam.Domain.BukuDomain
import com.example.klubbuku_kleponresingteam.R

class BukuActivity : AppCompatActivity() {

    private lateinit var adapter: BukuAdapter
    private lateinit var recyclerViewCourse: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buku)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val items = arrayListOf(
            BukuDomain("Ini adalah kategori buku Fantasi", "150", "unicorn_48"),
            BukuDomain("Ini adalah kategori buku Novel", "170", "ic_2"),
            BukuDomain("Ini adalah kategori buku fiksi", "600", "ic_3"),
            BukuDomain("Ini adalah kategori buku Anime", "999", "ic_4"),
            BukuDomain("Ini adalah kategori buku Komik", "910", "ic_5")
        )

        recyclerViewCourse = findViewById(R.id.viewKategori)
        recyclerViewCourse.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = BukuAdapter(items)
        recyclerViewCourse.adapter = adapter
    }
}
