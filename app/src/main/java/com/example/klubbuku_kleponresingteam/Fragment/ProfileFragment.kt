package com.example.klubbuku_kleponresingteam.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.klubbuku_kleponresingteam.Adapter.ArchiveAdapter
import com.example.klubbuku_kleponresingteam.Adapter.MyReviewAdapter
import com.example.klubbuku_kleponresingteam.Domain.MyReview
import com.example.klubbuku_kleponresingteam.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterArchieve: ArchiveAdapter
    private lateinit var adapterMyReview: MyReviewAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

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
        storage = FirebaseStorage.getInstance()

        loadUserProfile()

        initRecyclerViewArchieve()
        initRecyclerViewMyReview()

        binding.saveUsername.setOnClickListener {
            saveUsername()
        }

        binding.fotoProfile.setOnClickListener {
            openImageChooser()
        }
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
                            val photoUrl = document.getString("photoUrl")
                            binding.namaUser.setText(username)
                            if (photoUrl != null) {
                                Glide.with(this)
                                    .load(photoUrl)
                                    .circleCrop()
                                    .into(binding.fotoProfile)
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
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

    private fun saveUsername() {
        val newUsername = binding.namaUser.text.toString()
        val user = auth.currentUser
        user?.let {
            val userEmail = it.email
            if (userEmail != null) {
                db.collection("users")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val docId = document.id
                            db.collection("users").document(docId)
                                .update("username", newUsername)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Username updated", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(context, "Failed to update username", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Failed to find user", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            binding.fotoProfile.setImageURI(selectedImageUri)
            uploadImageToFirebase()
        }
    }

    private fun uploadImageToFirebase() {
        selectedImageUri?.let { uri ->
            val user = auth.currentUser
            user?.let {
                val ref = storage.reference.child("profile_images/${user.uid}")
                ref.putFile(uri)
                    .addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener { downloadUri ->
                            updatePhotoUrl(downloadUri.toString())
                        }.addOnFailureListener { e ->
                            Log.e("Firebase", "Failed to get download URL", e)
                            Toast.makeText(context, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firebase", "Failed to upload image", e)
                        Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun updatePhotoUrl(photoUrl: String) {
        val user = auth.currentUser
        user?.let {
            val userEmail = it.email
            if (userEmail != null) {
                db.collection("users")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val docId = document.id
                            db.collection("users").document(docId)
                                .update("photoUrl", photoUrl)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Photo updated", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(context, "Failed to update photo URL", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Failed to find user", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
