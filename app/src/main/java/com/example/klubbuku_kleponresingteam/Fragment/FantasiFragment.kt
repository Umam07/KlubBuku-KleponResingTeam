package com.example.klubbuku_kleponresingteam.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Adapter.CobaAdapter
import com.example.klubbuku_kleponresingteam.Domain.CobaDomain
import com.example.klubbuku_kleponresingteam.R
import com.example.klubbuku_kleponresingteam.databinding.FragmentFantasiBinding

class FantasiFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: CobaAdapter
    private lateinit var bookList: MutableList<CobaDomain>
    private lateinit var binding: FragmentFantasiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFantasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        bookList = mutableListOf()
        // Add books to the list (for demonstration purposes)
        bookList.add(CobaDomain("The Hunger Games", "Suzanne Collins", 4.34f, 8808170, "url_to_image"))
        bookList.add(CobaDomain("Harry Potter and the Order of the Phoenix", "J.K. Rowling", 4.5f, 3431281, "url_to_image"))
        bookList.add(CobaDomain("Pride and Prejudice", "Jane Austen", 4.29f, 4316523, "url_to_image"))
        bookList.add(CobaDomain("To Kill a Mockingbird", "Harper Lee", 4.26f, 6219435, "url_to_image"))

        bookAdapter = CobaAdapter(requireContext(), bookList)
        recyclerView.adapter = bookAdapter
    }
}