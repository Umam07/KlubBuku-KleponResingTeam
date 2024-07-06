package com.example.klubbuku_kleponresingteam.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.klubbuku_kleponresingteam.Fragment.FantasiFragment
import com.example.klubbuku_kleponresingteam.R

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        val buttonGetStarted = findViewById<AppCompatButton>(R.id.buttonGetStarted)
        buttonGetStarted.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
        }

    }
}