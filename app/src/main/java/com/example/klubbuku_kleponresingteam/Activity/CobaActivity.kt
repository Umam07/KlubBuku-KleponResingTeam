package com.example.klubbuku_kleponresingteam.Activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Adapter.CobaAdapter
import com.example.klubbuku_kleponresingteam.Domain.CobaDomain
import com.example.klubbuku_kleponresingteam.R
import com.example.klubbuku_kleponresingteam.databinding.ActivityCobaBinding

class CobaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: CobaAdapter
    private lateinit var bookList: MutableList<CobaDomain>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coba)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        bookList = mutableListOf()
        // Add books to the list (for demonstration purposes)
        bookList.add(CobaDomain("The Hunger Games", "Suzanne Collins", 4.34f, 8808170, "url_to_image"))
        bookList.add(CobaDomain("Harry Potter and the Order of the Phoenix", "J.K. Rowling", 4.5f, 3431281, "url_to_image"))
        bookList.add(CobaDomain("Pride and Prejudice", "Jane Austen", 4.29f, 4316523, "url_to_image"))
        bookList.add(CobaDomain("To Kill a Mockingbird", "Harper Lee", 4.26f, 6219435, "url_to_image"))

        bookAdapter = CobaAdapter(this, bookList)
        recyclerView.adapter = bookAdapter
        }
    }
