package com.example.klubbuku_kleponresingteam.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.klubbuku_kleponresingteam.Fragment.HomeFragment
import com.example.klubbuku_kleponresingteam.Fragment.SearchFragment
import com.example.klubbuku_kleponresingteam.Fragment.DiscoverFragment
import com.example.klubbuku_kleponresingteam.Fragment.ProfileFragment
import com.example.klubbuku_kleponresingteam.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)

        val fabUpload = findViewById<FloatingActionButton>(R.id.fabUpload)
        fabUpload.setOnClickListener {
            val intent = Intent(this, UploadReviewActivity::class.java)
            startActivity(intent)
        }






        // Set default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout, HomeFragment())
                .commit()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.nav_discover -> {
                    loadFragment(DiscoverFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout, fragment)
            .commit()
    }
}
