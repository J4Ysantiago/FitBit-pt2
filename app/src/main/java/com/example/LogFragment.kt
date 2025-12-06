package com.example.fitbitpt2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LogFragment : Fragment(R.layout.fragment_log) {

    private lateinit var db: CalorieDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = CalorieDatabase.getDatabase(requireContext())

        val foodInput = view.findViewById<EditText>(R.id.tvFood)
        val caloriesInput = view.findViewById<EditText>(R.id.tvCalories)
        val recordButton = view.findViewById<Button>(R.id.btnRecord)

        recordButton.setOnClickListener {
            val food = foodInput.text.toString()
            val calories = caloriesInput.text.toString().toIntOrNull() ?: 0

            if (food.isNotBlank()) {
                saveEntry(food, calories)
                foodInput.text.clear()
                caloriesInput.text.clear()
            }
        }
    }

    private fun saveEntry(food: String, calories: Int) {
        lifecycleScope.launch {
            db.calorieDao().insert(
                CalorieEntry(foodName = food, calories = calories)
            )
        }
    }
}