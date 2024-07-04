package com.example.klubbuku_kleponresingteam.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.klubbuku_kleponresingteam.Adapter.ArchiveAdapter
import com.example.klubbuku_kleponresingteam.Adapter.MyReviewAdapter
import com.example.klubbuku_kleponresingteam.Domain.MyReview
import com.example.klubbuku_kleponresingteam.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterArchieve: ArchiveAdapter
    private lateinit var adapterMyReview: MyReviewAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        loadUserProfile()

        initRecyclerViewArchieve()
        initRecyclerViewMyReview()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        user?.let {
            val userEmail = it.email
            if (userEmail != null) {
                db.collection("users")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val username = document.getString("username")
                            binding.namaUser.text = username
                            // Anda bisa mengisi TextView lainnya jika ada informasi tambahan
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Handle failure
                    }
            }
        }
    }

    private fun initRecyclerViewMyReview() {
        val items = arrayListOf(
            MyReview("My Review Books", ""),
            MyReview("Apes", ""),
            MyReview("Hitam", ""),
            MyReview("AAAAAA", "")
        )

        binding.viewMyReview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterMyReview = MyReviewAdapter(items)
        binding.viewMyReview.adapter = adapterMyReview
    }

    private fun initRecyclerViewArchieve() {
        val items = arrayListOf(
            "Best Novel Books",
            "Beginner Books",
            "Self-Improvement"
        )

        binding.viewArchieve.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterArchieve = ArchiveAdapter(items)
        binding.viewArchieve.adapter = adapterArchieve
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
