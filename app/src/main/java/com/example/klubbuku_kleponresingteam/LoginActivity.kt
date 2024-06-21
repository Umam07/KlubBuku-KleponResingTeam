package com.example.klubbuku_kleponresingteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import com.example.klubbuku_kleponresingteam.databinding.ActivityLoginBinding
import com.example.klubbuku_kleponresingteam.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth : FirebaseAuth
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val txtForgotPassword: TextView = findViewById(R.id.txtForgotPassword)


        auth = FirebaseAuth.getInstance()

        binding.goToSignUpActivity.setOnClickListener{
            val intent = Intent (this, SignUpActivity::class.java)
            startActivity(intent)
        }


        txtForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }


        binding.loginBtn.setOnClickListener{
            val email = binding.emailInputLogin.text.toString()
            val password = binding.passwordInputLogin.text.toString()

            // Validasi email
            if (email.isEmpty()){
                binding.emailInputLogin.error = "Email harus diisi"
                binding.emailInputLogin.requestFocus()
                return@setOnClickListener
            }

            // Validasi email tidak sesuai
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailInputLogin.error = "Email Tidak Valid"
                binding.emailInputLogin.requestFocus()
                return@setOnClickListener
            }

            // Validasi password
            if (password.isEmpty()){
                binding.passwordInputLogin.error = "Password harus diisi"
                binding.passwordInputLogin.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6){
                binding.passwordInputLogin.error = "Password minimal 6 Karakter"
                binding.passwordInputLogin.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }

    }

    private fun LoginFirebase(email: String, password: String) {
       auth.signInWithEmailAndPassword(email, password)
           .addOnCompleteListener(this){
               if (it.isSuccessful){
                   Toast.makeText(this, "Selamat Datang $email", Toast.LENGTH_SHORT).show()
                   val intent = Intent(this, Dashboard::class.java)
                   startActivity(intent)
               }else{
                   Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
               }
           }

    }

    // baru
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