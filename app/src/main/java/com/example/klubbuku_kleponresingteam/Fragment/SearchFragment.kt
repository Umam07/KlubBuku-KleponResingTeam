package com.example.klubbuku_kleponresingteam.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Adapter.UserAdapter
import com.example.klubbuku_kleponresingteam.Domain.User
import com.example.klubbuku_kleponresingteam.R
import com.example.klubbuku_kleponresingteam.databinding.FragmentSearchBinding
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        userList = arrayListOf()
        userAdapter = UserAdapter(userList)

        binding.userRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.userRecyclerView.setHasFixedSize(true)
        binding.userRecyclerView.adapter = userAdapter

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchUsers(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchUsers(query: String) {
        db.collection("users")
            .whereEqualTo("username", query)
            .get()
            .addOnSuccessListener { documents ->
                userList.clear()
                for (document in documents) {
                    val user = document.toObject(User::class.java)
                    userList.add(user)
                }
                userAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("SearchFragment", "Error getting documents: ", exception)
            }
    }
}
