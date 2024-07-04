package com.example.klubbuku_kleponresingteam.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.klubbuku_kleponresingteam.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.IOException

class UploadReviewActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var uploadImg: ImageView
    private lateinit var uploadJudul: EditText
    private lateinit var uploadPenulis: EditText
    private lateinit var uploadGenre: EditText
    private lateinit var uploadReviewmu: EditText
    private lateinit var saveButton: Button

    private lateinit var databaseReference: DatabaseReference // Referensi ke Firebase Realtime Database
    private lateinit var storageReference: StorageReference // Referensi ke Firebase Storage

    private var imageUri: Uri? = null // Uri untuk gambar yang dipilih

    // Deklarasikan PERMISSION_REQUEST_CODE sebagai konstanta
    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_review)

        // Inisialisasi variabel
        uploadImg = findViewById(R.id.uploadimg)
        uploadJudul = findViewById(R.id.uploadjudul)
        uploadPenulis = findViewById(R.id.uploadpenulis)
        uploadGenre = findViewById(R.id.uploadgenre)
        uploadReviewmu = findViewById(R.id.uploadreviewmu)
        saveButton = findViewById(R.id.savebutton)

        // Inisialisasi Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("reviews") // Sesuaikan path database
        storageReference = FirebaseStorage.getInstance().getReference()

        // Set listener untuk ImageView untuk membuka galeri
        uploadImg.setOnClickListener {
            openGallery()
        }

        // Set listener untuk Button "Save"
        saveButton.setOnClickListener {
            saveReview()
        }

        // Meminta Izin Akses Penyimpanan
        requestStoragePermission()
    }

    // Fungsi untuk meminta izin penyimpanan
    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_REQUEST_CODE)
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
            }
        }
    }

    // Fungsi untuk membuka galeri
    private fun openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            } else {
                // Minta izin jika belum diberikan
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_REQUEST_CODE)
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            } else {
                // Minta izin jika belum diberikan
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
            }
        }
    }

    // Handle hasil dari pemilihan gambar
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Log.d("UploadReview", "Image URI: $imageUri") // Cetak URI ke log

            try {
                val bitmap = getBitmapFromUri(imageUri!!)
                if (bitmap != null) {
                    uploadImg.setImageBitmap(bitmap)
                } else {
                    Log.e("UploadReview", "Error: Bitmap is null")
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                Log.e("UploadReview", "Error loading image: ${e.message}")
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk mendapatkan Bitmap dari URI
    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap? { // Kembalikan Bitmap? untuk menangani kemungkinan null
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
            if (fileDescriptor != null) { // Periksa fileDescriptor sebelum menguraikan
                val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor?.close()
                return image
            } else {
                Log.e("UploadReview", "Error: fileDescriptor is null")
                return null
            }
        } catch (e: IOException) {
            Log.e("UploadReview", "Error loading image: ${e.message}")
            return null
        }
    }

    // Fungsi untuk menyimpan review ke Firebase
    private fun saveReview() {
        val judul = uploadJudul.text.toString().trim()
        val penulis = uploadPenulis.text.toString().trim()
        val genre = uploadGenre.text.toString().trim()
        val review = uploadReviewmu.text.toString().trim()

        // Validasi input
        if (judul.isEmpty() || penulis.isEmpty() || genre.isEmpty() || review.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Upload gambar ke Firebase Storage
        if (imageUri != null) {
            val imageRef = storageReference.child("images/" + System.currentTimeMillis() + ".jpg") // Generate unique filename
            uploadImageToStorage(imageRef, judul, penulis, genre, review)
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk mengupload gambar ke Firebase Storage
    private fun uploadImageToStorage(imageRef: StorageReference, judul: String, penulis: String, genre: String, review: String) {
        try {
            val bitmap = getBitmapFromUri(imageUri!!)
            if (bitmap != null) { // Periksa apakah bitmap tidak null
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                // Upload data gambar ke Storage
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnSuccessListener {
                    Log.d("UploadReview", "Image uploaded successfully")
                    // Dapatkan URL gambar yang terupload
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        Log.d("UploadReview", "Image URL: $imageUrl")
                        // Simpan data ke Firebase Realtime Database
                        saveReviewToDatabase(imageUrl, judul, penulis, genre, review)
                    }.addOnFailureListener {
                        Log.e("UploadReview", "Error getting download URL: ${it.message}")
                        Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Log.e("UploadReview", "Error uploading image: ${it.message}")
                    Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("UploadReview", "Error: Bitmap is null")
                Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Log.e("UploadReview", "Error converting image to byte array: ${e.message}")
            Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk menyimpan review ke Firebase Realtime Database
    private fun saveReviewToDatabase(imageUrl: String, judul: String, penulis: String, genre: String, review: String) {
        // Buat objek HashMap untuk menyimpan data review
        val reviewData = HashMap<String, Any>()
        reviewData["judul"] = judul
        reviewData["penulis"] = penulis
        reviewData["genre"] = genre
        reviewData["review"] = review
        reviewData["imageUrl"] = imageUrl

        // Simpan data ke Firebase Realtime Database
        databaseReference.push().setValue(reviewData)
            .addOnSuccessListener {
                Log.d("UploadReview", "Review saved successfully")
                // Bersihkan input field
                uploadJudul.setText("")
                uploadPenulis.setText("")
                uploadGenre.setText("")
                uploadReviewmu.setText("")
                uploadImg.setImageResource(R.drawable.uploadimg) // Reset ImageView
                imageUri = null
                Toast.makeText(this, "Review berhasil disimpan", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.e("UploadReview", "Error saving review: ${it.message}")
                Toast.makeText(this, "Error saving review", Toast.LENGTH_SHORT).show()
            }
    }

    // Handle hasil dari permintaan izin
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, Anda dapat melanjutkan dengan kode yang memerlukan akses penyimpanan
                Toast.makeText(this, "Izin penyimpanan diberikan", Toast.LENGTH_SHORT).show()
            } else {
                // Izin ditolak, Anda perlu menangani kasus ini (misalnya, menampilkan pesan ke pengguna)
                Toast.makeText(this, "Izin penyimpanan ditolak", Toast.LENGTH_SHORT).show()
                // Jika izin ditolak, mungkin Anda ingin menampilkan pesan ke pengguna atau menghentikan fungsi yang memerlukan akses penyimpanan
                // Misalnya:
                // finish() // Tutup aktivitas
            }
        }
    }
}