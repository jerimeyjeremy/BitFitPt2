package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class InsertActivity : AppCompatActivity() {
    private lateinit var foodEditText: EditText
    private lateinit var caloriesEditText: EditText
    private lateinit var foodTextView: TextView
    private lateinit var caloriesTextView: TextView
    private lateinit var recordButton: Button

    var foods : MutableList<DisplayFit> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        recordButton = findViewById(R.id.recordBtn)
        foodEditText = findViewById(R.id.foodET)
        caloriesEditText = findViewById(R.id.caloriesET)
        foodTextView = findViewById(R.id.foodTV)
        caloriesTextView = findViewById(R.id.caloriesTV)

        recordButton.setOnClickListener {
            val foodName = foodEditText.text
            val foodCalories = caloriesEditText.text

            lifecycleScope.launch(IO) {
                (application as FoodApplication).db.foodDao().insert(
                    FitEntity(
                        name = foodName.toString(),
                        calories = foodCalories.toString().toDouble()
                    )
                )
            }

        }
    }
}