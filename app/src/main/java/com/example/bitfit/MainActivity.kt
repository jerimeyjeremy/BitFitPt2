package com.example.bitfit

import android.content.Intent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import com.example.bitfit.databinding.ActivityMainBinding
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.serialization.json.Json

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val articles = mutableListOf<DisplayFit>()
    private lateinit var userList:ArrayList<DisplayFit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Call helper method to swap the FrameLayout with the fragment
        replaceFragment(FitBitFragment())

        val fragmentManager: FragmentManager = supportFragmentManager

        // define your fragments here
        val dashboardFragment: Fragment = DashboardFragment()
        val fitBitFragment: Fragment = FitBitFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.nav_dashboard -> fragment = dashboardFragment
                R.id.nav_log -> fragment = fitBitFragment
            }
            fragmentManager.beginTransaction().replace(R.id.fitbit_frame_layout, fragment).commit()
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.nav_log


        //userList = ArrayList()
        val addButton = findViewById<Button>(R.id.addBtn)

        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity,InsertActivity::class.java)
            startActivity(intent)
        }
    }



    private fun replaceFragment(fitBitFragment: FitBitFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fitbit_frame_layout, fitBitFragment)
        fragmentTransaction.commit()
    }
}