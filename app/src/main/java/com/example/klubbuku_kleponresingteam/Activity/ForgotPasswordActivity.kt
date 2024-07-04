package com.example.klubbuku_kleponresingteam.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.klubbuku_kleponresingteam.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class ForgotPasswordActivity : AppCompatActivity() {

    // Declaration
    private lateinit var btnReset: Button
    private lateinit var btnBack: Button
    private lateinit var edtEmail: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var strEmail: String
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialization
        btnReset = findViewById(R.id.btnReset)
        edtEmail = findViewById(R.id.edtForgotPasswordEmail)
        progressBar = findViewById(R.id.forgetPasswordProgressbar)
        mAuth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        // Reset Button Listener
        btnReset.setOnClickListener {
            strEmail = edtEmail.text.toString().trim()
            if (!TextUtils.isEmpty(strEmail)) {
                resetPassword()
            } else {
                edtEmail.error = "Email field can't be empty"
            }
        }

    }

    private fun resetPassword() {
        progressBar.visibility = ProgressBar.VISIBLE
        btnReset.isEnabled = false

        mAuth.sendPasswordResetEmail(strEmail)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Reset Password link has been sent to your registered Email",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            .addOnFailureListener { e ->
                Toast.makeText(this, "Error :- " + e.message, Toast.LENGTH_SHORT).show()
                progressBar.visibility = ProgressBar.INVISIBLE
                btnReset.isEnabled = true
            }
    }

    //baru
    private fun updatePasswordInFirestore(email: String, newPassword: String) {
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("users").document(document.id)
                        .update("password", newPassword)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Password updated in Firestore", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error updating password in Firestore", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error fetching user document", Toast.LENGTH_SHORT).show()
            }
    }




}

