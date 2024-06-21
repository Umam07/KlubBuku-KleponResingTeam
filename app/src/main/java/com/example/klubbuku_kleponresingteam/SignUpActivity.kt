package com.example.klubbuku_kleponresingteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import com.example.klubbuku_kleponresingteam.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.goToLoginActivity.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signUpBtn.setOnClickListener{
            val username = binding.usernameInput.text.toString()
            val email = binding.emailInputSignUp.text.toString()
            val password = binding.passwordInputSignUp.text.toString()

            // Validasi email
            if (email.isEmpty()){
                binding.emailInputSignUp.error = "Email harus diisi"
                binding.emailInputSignUp.requestFocus()
                return@setOnClickListener
            }

            // Validasi email tidak sesuai
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailInputSignUp.error = "Email Tidak Valid"
                binding.emailInputSignUp.requestFocus()
                return@setOnClickListener
            }

            // Validasi password
            if (password.isEmpty()){
                binding.passwordInputSignUp.error = "Password harus diisi"
                binding.passwordInputSignUp.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6){
                binding.passwordInputSignUp.error = "Password minimal 6 Karakter"
                binding.passwordInputSignUp.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(username, email, password)

        }
    }

    private fun RegisterFirebase(username: String, email: String, password: String) {
        val TAG = "SignUpActivity"
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()

                    // Dapatkan instance Firestore
                    val db = FirebaseFirestore.getInstance()

                    // Simpan data ke koleksi "default"
                    val user = hashMapOf(
                        "username" to username,
                        "email" to email,
                        "password" to password
                    )
                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}