package com.example.klubbuku_kleponresingteam.Activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.DecelerateInterpolator
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
            // Membuat intent untuk LoginActivity
            val intent = Intent(this, LoginActivity::class.java)

            // Membuat animasi pergeseran ke kanan
            val slideOut = ObjectAnimator.ofFloat(binding.root, View.TRANSLATION_X, 0f, binding.root.width.toFloat())
            slideOut.duration = 200 // Durasi animasi
            slideOut.interpolator = DecelerateInterpolator() // Interpolator untuk animasi yang lebih halus

            // Menjalankan animasi
            slideOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Setelah animasi selesai, jalankan intent
                    startActivity(intent)
                    overridePendingTransition(0, 0) // Menghilangkan animasi default dari Android
                }
            })
            slideOut.start()
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
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()

                    val user = auth.currentUser
                    val db = FirebaseFirestore.getInstance()

                    val userMap = hashMapOf(
                        "uid" to user?.uid,
                        "username" to username,
                        "email" to email,
                        "password" to password
                    )

                    user?.let {
                        db.collection("users").document(user.uid)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot added with ID: ${user.uid}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}