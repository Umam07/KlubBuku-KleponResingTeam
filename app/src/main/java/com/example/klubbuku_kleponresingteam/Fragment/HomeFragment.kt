package com.example.klubbuku_kleponresingteam.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Activity.ReviewAdapter
import com.example.klubbuku_kleponresingteam.Activity.UploadReviewActivity
import com.example.klubbuku_kleponresingteam.Domain.Review
import com.example.klubbuku_kleponresingteam.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var reviewList: ArrayList<Review>
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var storageReference: StorageReference
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        storageReference = FirebaseStorage.getInstance().reference
        firestore = FirebaseFirestore.getInstance()



        reviewRecyclerView = view.findViewById(R.id.viewOngoing)
        reviewRecyclerView.layoutManager = LinearLayoutManager(activity)
        reviewRecyclerView.setHasFixedSize(true)

        reviewList = arrayListOf()
        reviewAdapter = ReviewAdapter(reviewList)
        reviewRecyclerView.adapter = reviewAdapter

        databaseReference = FirebaseDatabase.getInstance().getReference("reviews")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                reviewList.clear()
                for (dataSnapshot in snapshot.children) {
                    val review = dataSnapshot.getValue(Review::class.java)
                    if (review != null) {
                        reviewList.add(review)
                    }
                }
                reviewAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Dashboard", "Database error: ${error.message}")
            }
        })

        return view
    }
}
