package com.example.klubbuku_kleponresingteam.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Adapter.BukuAdapter
import com.example.klubbuku_kleponresingteam.Domain.BukuDomain
import com.example.klubbuku_kleponresingteam.R

class DiscoverFragment : Fragment(), BukuAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BukuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout fragment dan inisialisasi RecyclerView
        val view = inflater.inflate(R.layout.fragment_discover, container, false)
        recyclerView = view.findViewById(R.id.viewKategori)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inisialisasi adapter dan set listener
        val items = arrayListOf(
            BukuDomain("Ini adalah kategori buku Fantasi", "150", "ic_1"),
            BukuDomain("Ini adalah kategori buku Novel", "170", "ic_2"),
            BukuDomain("Ini adalah kategori buku fiksi", "600", "ic_3"),
            BukuDomain("Ini adalah kategori buku Anime", "999", "ic_4"),
            BukuDomain("Ini adalah kategori buku Komik", "910", "ic_5")
        )
        adapter = BukuAdapter(items)
        recyclerView.adapter = adapter

        // Set listener untuk adapter
        adapter.setOnItemClickListener(this)

        return view
    }

    // Implementasi onItemClick untuk navigasi ke fragment lain
    override fun onItemClick(position: Int) {
        // Lakukan navigasi ke FantasiFragment jika item pertama di klik
        if (position == 0) {
            //findNavController().navigate(R.id.action_discoverFragment_to_fantasiFragment)
        }
    }
}
